package br.ufscar.dc.dsw.Projeto2DSW.controller;

import br.ufscar.dc.dsw.Projeto2DSW.model.Projeto;
import br.ufscar.dc.dsw.Projeto2DSW.model.Usuario;
import br.ufscar.dc.dsw.Projeto2DSW.repository.ProjetoRepository;
import br.ufscar.dc.dsw.Projeto2DSW.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/projetos")
public class ProjetoController {

    @Autowired
    private ProjetoRepository projetoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("")
    public String projetos() {
        return "admin/projetos/projetos";
    }

    @GetMapping("/listar-projetos")
    public String listarProjetos() {
        return "admin/projetos/listar-projetos";
    }

    @GetMapping("/novo-projeto")
    public String novoProjeto(Model model) {
        model.addAttribute("projeto", new Projeto());
        model.addAttribute("usuariosDisponiveis", usuarioRepository.findAll());
        return "admin/projetos/novo-projeto";
    }

    @PostMapping("/novo-projeto")
    public String novoProjeto(@ModelAttribute Projeto projeto, @RequestParam List<Long> usuarios) {
        List<Usuario> usuariosDisponiveis = usuarioRepository.findAllById(usuarios);
        projeto.setUsuarios(usuariosDisponiveis);

        projetoRepository.save(projeto);
        return "redirect:/admin/projetos/listar-projetos";
    }
}
