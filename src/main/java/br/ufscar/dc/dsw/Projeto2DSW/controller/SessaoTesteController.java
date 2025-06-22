package br.ufscar.dc.dsw.Projeto2DSW.controller;

import br.ufscar.dc.dsw.Projeto2DSW.model.Estrategia;
import br.ufscar.dc.dsw.Projeto2DSW.model.Projeto;
import br.ufscar.dc.dsw.Projeto2DSW.model.SessaoTeste;
import br.ufscar.dc.dsw.Projeto2DSW.model.Status;
import br.ufscar.dc.dsw.Projeto2DSW.model.Usuario;
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

@Controller
@RequestMapping("/testador/sessoes")
public class SessaoTesteController {

    @Autowired
    private SessaoTesteRepository sessaoTesteRepository;

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private EstrategiaRepository estrategiaRepository;

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

    @GetMapping("")
    public String listarSessoes(@AuthenticationPrincipal Usuario usuarioLogado, Model model) {
        List<SessaoTeste> sessoes = sessaoTesteRepository.findByUsuario_IdUsuario(usuarioLogado.getId_usuario());
        model.addAttribute("sessoes", sessoes);
        return "testador/sessoes/listar-sessoes";
    }
}
