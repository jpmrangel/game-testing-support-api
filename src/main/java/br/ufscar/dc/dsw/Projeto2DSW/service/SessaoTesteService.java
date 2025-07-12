package br.ufscar.dc.dsw.Projeto2DSW.service;

import br.ufscar.dc.dsw.Projeto2DSW.dto.BugCreateDTO;
import br.ufscar.dc.dsw.Projeto2DSW.dto.SessaoCreateDTO;
import br.ufscar.dc.dsw.Projeto2DSW.model.*;
import br.ufscar.dc.dsw.Projeto2DSW.repository.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

@Service
@Transactional
public class SessaoTesteService {

    @Autowired
    private SessaoTesteRepository sessaoTesteRepository;
    @Autowired
    private ProjetoRepository projetoRepository;
    @Autowired
    private EstrategiaRepository estrategiaRepository;
    @Autowired
    private BugRepository bugRepository;

    @Transactional(readOnly = true)
    public List<SessaoTeste> listarPorUsuario(Long usuarioId) {
        return sessaoTesteRepository.findByUsuario_IdUsuario(usuarioId);
    }

    @Transactional(readOnly = true)
    public SessaoTeste buscarPorId(Long id) {
        return sessaoTesteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sessão não encontrada."));
    }

    public SessaoTeste criar(@Valid SessaoCreateDTO dto, Usuario usuarioLogado) {
        Projeto projeto = projetoRepository.findById(dto.getProjetoId())
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado."));

        if (projeto.getUsuarios().stream().noneMatch(u -> u.getId_usuario().equals(usuarioLogado.getId_usuario()))) {
            throw new SecurityException("Usuário não tem permissão para criar sessão neste projeto.");
        }

        Estrategia estrategia = estrategiaRepository.findById(dto.getEstrategiaId())
                .orElseThrow(() -> new RuntimeException("Estratégia não encontrada."));

        SessaoTeste sessao = new SessaoTeste();
        sessao.setProjeto(projeto);
        sessao.setEstrategia(estrategia);
        sessao.setTempo(dto.getTempo());
        sessao.setDescricao(dto.getDescricao());
        sessao.setUsuario(usuarioLogado);
        sessao.setNome_testador(usuarioLogado.getNome());
        sessao.setStatus(Status.CRIADO);

        return sessaoTesteRepository.save(sessao);
    }

    public SessaoTeste iniciar(Long id, Usuario usuarioLogado) {
        SessaoTeste sessao = buscarEValidarPermissao(id, usuarioLogado);

        if (sessao.getStatus() != Status.CRIADO) {
            throw new IllegalStateException("A sessão não pode ser iniciada, pois não está no status 'CRIADO'.");
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

        return sessao;
    }

    public SessaoTeste finalizar(Long id, Usuario usuarioLogado) {
        SessaoTeste sessao = buscarEValidarPermissao(id, usuarioLogado);

        if (sessao.getStatus() != Status.EM_EXECUCAO) {
            throw new IllegalStateException("A sessão não pode ser finalizada, pois não está 'EM EXECUÇÃO'.");
        }

        sessao.setStatus(Status.FINALIZADO);
        sessao.setData_fim(new Timestamp(System.currentTimeMillis()));
        return sessaoTesteRepository.save(sessao);
    }

    public Bug reportarBug(Long sessaoId, @Valid BugCreateDTO dto, Usuario usuarioLogado) {
        SessaoTeste sessao = buscarEValidarPermissao(sessaoId, usuarioLogado);

        if (sessao.getStatus() != Status.EM_EXECUCAO) {
            throw new IllegalStateException("Não é possível reportar bugs, a sessão não está 'EM EXECUÇÃO'.");
        }

        Bug bug = new Bug();
        bug.setDescricao(dto.getDescricao());
        bug.setSessao(sessao);
        return bugRepository.save(bug);
    }

    @Transactional(readOnly = true)
    public SessaoTeste buscarEValidarPermissao(Long id, Usuario usuarioLogado) {
        SessaoTeste sessao = buscarPorId(id);
        if (!Objects.equals(sessao.getUsuario().getId_usuario(), usuarioLogado.getId_usuario())
                && !usuarioLogado.getPapel().equals(Papel.ADMINISTRADOR)) {
            throw new SecurityException("Acesso negado a este recurso.");
        }
        return sessao;
    }
}