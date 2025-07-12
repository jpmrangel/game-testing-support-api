package br.ufscar.dc.dsw.Projeto2DSW.controller;

import br.ufscar.dc.dsw.Projeto2DSW.dto.BugCreateDTO;
import br.ufscar.dc.dsw.Projeto2DSW.dto.BugResponseDTO;
import br.ufscar.dc.dsw.Projeto2DSW.dto.SessaoCreateDTO;
import br.ufscar.dc.dsw.Projeto2DSW.dto.SessaoDetalhesResponseDTO;
import br.ufscar.dc.dsw.Projeto2DSW.dto.SessaoResponseDTO;
import br.ufscar.dc.dsw.Projeto2DSW.model.*;
import br.ufscar.dc.dsw.Projeto2DSW.repository.*;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

@Controller
@RestController
@RequestMapping("/api/testador/sessoes")
public class SessaoTesteController {
    
    @Autowired
    private SessaoTesteRepository sessaoTesteRepository;
    
    @Autowired
    private ProjetoRepository projetoRepository;
    
    @Autowired
    private EstrategiaRepository estrategiaRepository;
    
    @Autowired
    private BugRepository bugRepository;
    
    @GetMapping
    public ResponseEntity<List<SessaoResponseDTO>> listarMinhasSessoes(@AuthenticationPrincipal Usuario usuarioLogado) {
        List<SessaoTeste> sessoes = sessaoTesteRepository.findByUsuario_IdUsuario(usuarioLogado.getId_usuario());
        
        List<SessaoResponseDTO> sessoesDTO = sessoes.stream()
                .map(SessaoResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(sessoesDTO);
    }

    @PostMapping
    @PreAuthorize("hasRole('TESTADOR')")
    public ResponseEntity<SessaoResponseDTO> criarSessao(
            @Valid @RequestBody SessaoCreateDTO sessaoDTO,
            @AuthenticationPrincipal Usuario usuarioLogado) {

        Optional<Projeto> projetoOpt = projetoRepository.findById(sessaoDTO.getProjetoId());
        if (projetoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Optional<Estrategia> estrategiaOpt = estrategiaRepository.findById(sessaoDTO.getEstrategiaId());
        if (estrategiaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Projeto projeto = projetoOpt.get();
        if (projeto.getUsuarios().stream().noneMatch(u -> u.getId_usuario().equals(usuarioLogado.getId_usuario()))) {
            return ResponseEntity.badRequest().build();
        }
        
        SessaoTeste sessao = new SessaoTeste();
        sessao.setProjeto(projeto);
        sessao.setEstrategia(estrategiaOpt.get());
        sessao.setTempo(sessaoDTO.getTempo());
        sessao.setDescricao(sessaoDTO.getDescricao());
        sessao.setUsuario(usuarioLogado);
        sessao.setNome_testador(usuarioLogado.getNome());
        sessao.setStatus(Status.CRIADO);
        sessao.setData_criacao(new Timestamp(System.currentTimeMillis()));

        SessaoTeste sessaoSalva = sessaoTesteRepository.save(sessao);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SessaoResponseDTO(sessaoSalva));
    }
        
    @GetMapping("/{id}")
    public ResponseEntity<SessaoDetalhesResponseDTO> detalhesSessao(@PathVariable Long id, @AuthenticationPrincipal Usuario usuarioLogado) {
        Optional<SessaoTeste> sessaoOpt = sessaoTesteRepository.findById(id);
        if (sessaoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        SessaoTeste sessao = sessaoOpt.get();
        
        if (!Objects.equals(sessao.getUsuario().getId_usuario(), usuarioLogado.getId_usuario()) && !usuarioLogado.getPapel().equals(Papel.ADMINISTRADOR)) {
            return ResponseEntity.badRequest().build();
        }
        
        return ResponseEntity.ok(new SessaoDetalhesResponseDTO(sessao));
    }

    @PostMapping("/{id}/iniciar")
    public ResponseEntity<SessaoResponseDTO> iniciarSessao(@PathVariable Long id, @AuthenticationPrincipal Usuario usuarioLogado) {
        Optional<SessaoTeste> sessaoOpt = sessaoTesteRepository.findById(id);
        if (sessaoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        SessaoTeste sessao = sessaoOpt.get();

        if (!Objects.equals(sessao.getUsuario().getId_usuario(), usuarioLogado.getId_usuario())) {
            return ResponseEntity.badRequest().build();
        }
        
        if (sessao.getStatus() != Status.CRIADO) {
            return ResponseEntity.badRequest().build();
        }

        sessao.setStatus(Status.EM_EXECUCAO);
        sessao.setData_inicio(new Timestamp(System.currentTimeMillis()));
        sessaoTesteRepository.save(sessao);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                sessaoTesteRepository.findById(id).ifPresent(s -> {
                    if (s.getStatus() == Status.EM_EXECUCAO) {
                        s.setStatus(Status.FINALIZADO);
                        s.setData_fim(new Timestamp(System.currentTimeMillis()));
                        sessaoTesteRepository.save(s);
                    }
                });
            }
        }, (long) sessao.getTempo() * 60 * 1000);

        return ResponseEntity.ok(new SessaoResponseDTO(sessao));
    }

    @PostMapping("/{id}/finalizar")
    public ResponseEntity<SessaoResponseDTO> finalizarSessao(@PathVariable Long id, @AuthenticationPrincipal Usuario usuarioLogado) {
        Optional<SessaoTeste> sessaoOpt = sessaoTesteRepository.findById(id);
        if (sessaoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        SessaoTeste sessao = sessaoOpt.get();
        
        if (!Objects.equals(sessao.getUsuario().getId_usuario(), usuarioLogado.getId_usuario())) {
            return ResponseEntity.badRequest().build();
        }

        if (sessao.getStatus() != Status.EM_EXECUCAO) {
            return ResponseEntity.badRequest().build();
        }

        sessao.setStatus(Status.FINALIZADO);
        sessao.setData_fim(new Timestamp(System.currentTimeMillis()));
        sessaoTesteRepository.save(sessao);

        return ResponseEntity.ok(new SessaoResponseDTO(sessao));
    }

    @PostMapping("/{id}/bugs")
    public ResponseEntity<BugResponseDTO> reportarBug(
            @PathVariable Long id,
            @Valid @RequestBody BugCreateDTO bugDTO,
            @AuthenticationPrincipal Usuario usuarioLogado) {

        Optional<SessaoTeste> sessaoOpt = sessaoTesteRepository.findById(id);
        if (sessaoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        SessaoTeste sessao = sessaoOpt.get();
        
        if (!Objects.equals(sessao.getUsuario().getId_usuario(), usuarioLogado.getId_usuario())) {
            return ResponseEntity.badRequest().build();
        }

        if (sessao.getStatus() != Status.EM_EXECUCAO) {
            return ResponseEntity.badRequest().build();
        }

        Bug bug = new Bug();
        bug.setDescricao(bugDTO.getDescricao());
        bug.setSessao(sessao);
        Bug bugSalvo = bugRepository.save(bug);

        return ResponseEntity.status(HttpStatus.CREATED).body(new BugResponseDTO(bugSalvo));
    }

}