package br.ufscar.dc.dsw.Projeto2DSW.controller;

import br.ufscar.dc.dsw.Projeto2DSW.model.Usuario;
import br.ufscar.dc.dsw.Projeto2DSW.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/testador/projetos")
public class ProjetoTestadorController {

    @Autowired
    ProjetoRepository projetoRepository;

    @GetMapping("")
    public String projetos() {
        return "testador/projetos/projetos";
    }

    @GetMapping("/listar-projetos")
    public String listarProjetos(@AuthenticationPrincipal Usuario usuarioLogado, Model model) {
        model.addAttribute("projetos", projetoRepository.findByUsuarios_IdUsuario(usuarioLogado.getId_usuario()));
        return "testador/projetos/listar-projetos";
    }
}
