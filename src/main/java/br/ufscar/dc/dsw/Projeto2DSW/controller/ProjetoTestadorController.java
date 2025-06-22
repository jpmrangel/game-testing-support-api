package br.ufscar.dc.dsw.Projeto2DSW.controller;

import br.ufscar.dc.dsw.Projeto2DSW.model.Usuario;
import br.ufscar.dc.dsw.Projeto2DSW.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
    public String listarProjetos(@AuthenticationPrincipal Usuario usuarioLogado,
                                 @RequestParam(defaultValue = "nome") String sortField,
                                 @RequestParam(defaultValue = "asc") String sortDir,
                                 Model model) {

        List<String> camposPermitidos = List.of("nome", "dataCriacao");
        if (!camposPermitidos.contains(sortField)) {
            sortField = "nome";
        }

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        model.addAttribute("projetos", projetoRepository.findByUsuarios_IdUsuario(usuarioLogado.getId_usuario(), sort));
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "testador/projetos/listar-projetos";
    }
}
