package br.ufscar.dc.dsw.Projeto2DSW.controller;

import br.ufscar.dc.dsw.Projeto2DSW.model.Projeto;
import br.ufscar.dc.dsw.Projeto2DSW.model.Usuario;
import br.ufscar.dc.dsw.Projeto2DSW.repository.ProjetoRepository;
import br.ufscar.dc.dsw.Projeto2DSW.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/projetos")
public class ProjetoAdminController {

    @Autowired
    private ProjetoRepository projetoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("")
    public String projetos() {
        return "admin/projetos/projetos";
    }

    @GetMapping("/listar-projetos")
    public String listarProjetos(@RequestParam(defaultValue = "nome") String sortField,
                                 @RequestParam(defaultValue = "asc") String sortDir,
                                 Model model) {

        List<String> camposPermitidos = List.of("nome", "dataCriacao");
        if (!camposPermitidos.contains(sortField)) {
            sortField = "nome";
        }

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        model.addAttribute("projetos", projetoRepository.findAll(sort));
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

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

    @GetMapping("/editar/{id}")
    public String editarProjeto(@PathVariable("id") Long id, Model model) {
        Projeto projeto = projetoRepository.findById(id).orElseThrow();
        model.addAttribute("projeto", projeto);
        model.addAttribute("usuariosDisponiveis", usuarioRepository.findAll());
        return "admin/projetos/editar-projeto";
    }

    @PostMapping("/editar/{id}")
    public String editarProjeto(@PathVariable Long id, Projeto projeto, @RequestParam(required = false) List<Long> usuarios) {
        Projeto projetoOriginal = projetoRepository.findById(id).orElseThrow();

        projetoOriginal.setNome(projeto.getNome());
        projetoOriginal.setDescricao(projeto.getDescricao());

        if(usuarios != null) {
            List<Usuario> usuariosDisponiveis = usuarioRepository.findAllById(usuarios);
            projetoOriginal.setUsuarios(usuariosDisponiveis);
        } else {
            projetoOriginal.setUsuarios(new ArrayList<>());
        }

        projetoRepository.save(projetoOriginal);
        return "redirect:/admin/projetos/listar-projetos";
    }

    @PostMapping("/excluir/{id}")
    public String excluirProjeto(@PathVariable Long id) {
        projetoRepository.deleteById(id);
        return "redirect:/admin/projetos/listar-projetos";
    }
}