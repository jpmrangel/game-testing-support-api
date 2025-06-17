package br.ufscar.dc.dsw.Projeto2DSW.controller;

import br.ufscar.dc.dsw.Projeto2DSW.repository.EstrategiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/listar-estrategias")
public class EstrategiaVisitanteController {

    @Autowired
    EstrategiaRepository estrategiaRepository;

    @GetMapping
    public String listarEstrategias(Model model) {
        model.addAttribute("estrategias", estrategiaRepository.findAll());
        return "/listar-estrategias";
    }
}
