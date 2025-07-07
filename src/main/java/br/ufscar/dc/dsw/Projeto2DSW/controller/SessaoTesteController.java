package br.ufscar.dc.dsw.Projeto2DSW.controller;

import br.ufscar.dc.dsw.Projeto2DSW.model.Bug;
import br.ufscar.dc.dsw.Projeto2DSW.model.Projeto;
import br.ufscar.dc.dsw.Projeto2DSW.model.SessaoTeste;
import br.ufscar.dc.dsw.Projeto2DSW.model.Status;
import br.ufscar.dc.dsw.Projeto2DSW.model.Usuario;
import br.ufscar.dc.dsw.Projeto2DSW.repository.BugRepository;
import br.ufscar.dc.dsw.Projeto2DSW.repository.EstrategiaRepository;
import br.ufscar.dc.dsw.Projeto2DSW.repository.ProjetoRepository;
import br.ufscar.dc.dsw.Projeto2DSW.repository.SessaoTesteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Controller
@RequestMapping("/testador/sessoes")
public class SessaoTesteController {

    @Autowired
    private SessaoTesteRepository sessaoTesteRepository;

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private EstrategiaRepository estrategiaRepository;

    @Autowired
    private BugRepository bugRepository;

    @GetMapping("/nova")
    public String novaSessao(@RequestParam("projetoId") Long projetoId, Model model) {
        Projeto projeto = projetoRepository.findById(projetoId).orElseThrow();
        SessaoTeste sessao = new SessaoTeste();
        sessao.setProjeto(projeto);
        model.addAttribute("sessao", sessao);
        model.addAttribute("estrategias", estrategiaRepository.findAll());
        return "testador/sessoes/nova-sessao";
    }

    @PostMapping("/nova")
    public String salvarSessao(@ModelAttribute @Valid SessaoTeste sessao,
                               @AuthenticationPrincipal Usuario usuarioLogado) {
        sessao.setUsuario(usuarioLogado);
        sessao.setNome_testador(usuarioLogado.getNome());
        sessao.setStatus(Status.CRIADO);
        sessao.setData_criacao(new Timestamp(System.currentTimeMillis()));

        sessaoTesteRepository.save(sessao);
        return "redirect:/testador/projetos/listar-projetos";
    }

    @GetMapping("/listar-sessoes")
    public String listarSessoes(@AuthenticationPrincipal Usuario usuarioLogado, Model model) {
        List<SessaoTeste> sessoes = sessaoTesteRepository.findByUsuario_IdUsuario(usuarioLogado.getId_usuario());
        model.addAttribute("sessoes", sessoes);
        return "testador/sessoes/listar-sessoes";
    }

    @GetMapping("/iniciar/{id}")
    public String iniciarSessao(@PathVariable Long id, Model model) {
        SessaoTeste sessao = sessaoTesteRepository.findById(id).orElseThrow();
        
        if (sessao.getStatus() == Status.CRIADO) {
            sessao.setStatus(Status.EM_EXECUCAO);
            sessao.setData_inicio(new Timestamp(System.currentTimeMillis()));
            sessaoTesteRepository.save(sessao);
            
            // Configura timer para finalização automática
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    SessaoTeste sessaoAtualizada = sessaoTesteRepository.findById(id).orElse(null);
                    if (sessaoAtualizada != null && sessaoAtualizada.getStatus() == Status.EM_EXECUCAO) {
                        sessaoAtualizada.setStatus(Status.FINALIZADO);
                        sessaoAtualizada.setData_fim(new Timestamp(System.currentTimeMillis()));
                        sessaoTesteRepository.save(sessaoAtualizada);
                    }
                }
            }, sessao.getTempo() * 60 * 1000); // Converte minutos para milissegundos
        }
        
        return "redirect:/testador/sessoes/detalhes/" + id;
    }

    @GetMapping("/finalizar/{id}")
    public String finalizarSessao(@PathVariable Long id) {
        SessaoTeste sessao = sessaoTesteRepository.findById(id).orElseThrow();
        
        if (sessao.getStatus() == Status.EM_EXECUCAO) {
            sessao.setStatus(Status.FINALIZADO);
            sessao.setData_fim(new Timestamp(System.currentTimeMillis()));
            sessaoTesteRepository.save(sessao);
        }
        
        return "redirect:/testador/sessoes/listar-sessoes";
    }

    @GetMapping("/detalhes/{id}")
    public String detalhesSessao(@PathVariable Long id, Model model) {
        SessaoTeste sessao = sessaoTesteRepository.findById(id).orElseThrow();
        List<Bug> bugs = bugRepository.findBySessao_IdSessao(id);

        model.addAttribute("sessao", sessao);
        model.addAttribute("bugs", bugs);
        model.addAttribute("novoBug", new Bug());

        // Adicionar timestamp de início se disponível
        if(sessao.getData_inicio() != null) {
            model.addAttribute("dataInicioTimestamp", sessao.getData_inicio().getTime());
        }

        return "testador/sessoes/detalhes-sessao";
    }

    @PostMapping("/reportar-bug/{id}")
    public String reportarBug(@PathVariable Long id, 
                            @ModelAttribute("novoBug") Bug bug,
                            @AuthenticationPrincipal Usuario usuarioLogado) {
        SessaoTeste sessao = sessaoTesteRepository.findById(id).orElseThrow();
        
        if (sessao.getStatus() == Status.EM_EXECUCAO) {
            bug.setSessao(sessao);
            bugRepository.save(bug);
        }
        
        return "redirect:/testador/sessoes/detalhes/" + id;
    }
}
