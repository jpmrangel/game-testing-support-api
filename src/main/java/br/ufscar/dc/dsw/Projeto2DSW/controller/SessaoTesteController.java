package br.ufscar.dc.dsw.Projeto2DSW.controller;

import br.ufscar.dc.dsw.Projeto2DSW.dto.BugCreateDTO;
import br.ufscar.dc.dsw.Projeto2DSW.dto.BugResponseDTO;
import br.ufscar.dc.dsw.Projeto2DSW.dto.SessaoCreateDTO;
import br.ufscar.dc.dsw.Projeto2DSW.dto.SessaoDetalhesResponseDTO;
import br.ufscar.dc.dsw.Projeto2DSW.dto.SessaoResponseDTO;
import br.ufscar.dc.dsw.Projeto2DSW.model.SessaoTeste;
import br.ufscar.dc.dsw.Projeto2DSW.model.Usuario;
import br.ufscar.dc.dsw.Projeto2DSW.service.SessaoTesteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/testador/sessoes")
public class SessaoTesteController {

    @Autowired
    private SessaoTesteService service;

    @GetMapping
    public ResponseEntity<List<SessaoResponseDTO>> listarMinhasSessoes(@AuthenticationPrincipal Usuario usuarioLogado) {
        List<SessaoResponseDTO> sessoesDTO = service.listarPorUsuario(usuarioLogado.getId_usuario()).stream()
                .map(SessaoResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(sessoesDTO);
    }
    
    @PostMapping
    public ResponseEntity<SessaoResponseDTO> criarSessao(
            @Valid @RequestBody SessaoCreateDTO sessaoDTO,
            @AuthenticationPrincipal Usuario usuarioLogado) {
        try {
            SessaoTeste sessaoSalva = service.criar(sessaoDTO, usuarioLogado);
            return ResponseEntity.status(HttpStatus.CREATED).body(new SessaoResponseDTO(sessaoSalva));
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
        
    @GetMapping("/{id}")
    public ResponseEntity<SessaoDetalhesResponseDTO> detalhesSessao(@PathVariable Long id, @AuthenticationPrincipal Usuario usuarioLogado) {
        try {
            SessaoTeste sessao = service.buscarEValidarPermissao(id, usuarioLogado);
            return ResponseEntity.ok(new SessaoDetalhesResponseDTO(sessao));
        } catch (SecurityException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/{id}/iniciar")
    public ResponseEntity<SessaoResponseDTO> iniciarSessao(@PathVariable Long id, @AuthenticationPrincipal Usuario usuarioLogado) {
        try {
            SessaoTeste sessao = service.iniciar(id, usuarioLogado);
            return ResponseEntity.ok(new SessaoResponseDTO(sessao));
        } catch (IllegalStateException | SecurityException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/{id}/finalizar")
    public ResponseEntity<SessaoResponseDTO> finalizarSessao(@PathVariable Long id, @AuthenticationPrincipal Usuario usuarioLogado) {
        try {
            SessaoTeste sessao = service.finalizar(id, usuarioLogado);
            return ResponseEntity.ok(new SessaoResponseDTO(sessao));
        } catch (IllegalStateException | SecurityException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/{id}/bugs")
    public ResponseEntity<BugResponseDTO> reportarBug(
            @PathVariable Long id,
            @Valid @RequestBody BugCreateDTO bugDTO,
            @AuthenticationPrincipal Usuario usuarioLogado) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new BugResponseDTO(service.reportarBug(id, bugDTO, usuarioLogado)));
        } catch (IllegalStateException | SecurityException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}