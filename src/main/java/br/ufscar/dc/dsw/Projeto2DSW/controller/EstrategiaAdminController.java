package br.ufscar.dc.dsw.Projeto2DSW.controller;

import br.ufscar.dc.dsw.Projeto2DSW.model.Estrategia;
import br.ufscar.dc.dsw.Projeto2DSW.repository.EstrategiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/estrategias")
public class EstrategiaAdminController {

    @Autowired
    private EstrategiaRepository estrategiaRepository;

    @GetMapping("")
    public String estrategias() {
        return "admin/estrategias/estrategias";
    }

    @GetMapping("/listar-estrategias")
    public String listarEstrategias(Model model) {
        model.addAttribute("estrategias", estrategiaRepository.findAll());
        return "admin/estrategias/listar-estrategias";
    }

    @GetMapping("/nova-estrategia")
    public String novaEstrategia(Model model) {
        model.addAttribute("estrategia", new Estrategia());
        return "admin/estrategias/nova-estrategia";
    }

    @PostMapping("/nova-estrategia")
    public String novaEstrategia(@ModelAttribute Estrategia estrategia) {

        estrategiaRepository.save(estrategia);
        return "redirect:/admin/estrategias/listar-estrategias";
    }

    @GetMapping("/editar/{id}")
    public String editarEstrategia(@PathVariable("id") Long id, Model model) {
        Estrategia estrategia = estrategiaRepository.findById(id).orElseThrow();
        model.addAttribute("estrategia", estrategia);
        return "admin/estrategias/editar-estrategia";
    }

    @PostMapping("/editar/{id}")
    public String editarEstrategia(@PathVariable Long id, Estrategia estrategia) {
        Estrategia estrategiaOriginal = estrategiaRepository.findById(id).orElseThrow();

        estrategiaOriginal.setNome(estrategia.getNome());
        estrategiaOriginal.setDescricao(estrategia.getDescricao());
        estrategiaOriginal.setExemplo(estrategia.getExemplo());
        estrategiaOriginal.setDica(estrategia.getDica());

        estrategiaRepository.save(estrategiaOriginal);
        return "redirect:/admin/estrategias/listar-estrategias";
    }

    @PostMapping("/excluir/{id}")
    public String excluirEstrategia(@PathVariable Long id) {
        estrategiaRepository.deleteById(id);
        return "redirect:/admin/estrategias/listar-estrategias";
    }
}
