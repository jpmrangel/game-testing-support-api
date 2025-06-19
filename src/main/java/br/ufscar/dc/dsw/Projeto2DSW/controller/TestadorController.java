package br.ufscar.dc.dsw.Projeto2DSW.controller;

import br.ufscar.dc.dsw.Projeto2DSW.model.Usuario;
import br.ufscar.dc.dsw.Projeto2DSW.model.Papel;
import br.ufscar.dc.dsw.Projeto2DSW.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/testadores")
public class TestadorController {

    @Autowired
    private UserService service;

    @GetMapping
    public String listarTestadores(Model model) {
        model.addAttribute("testadores", service.listarPorPapel(Papel.TESTADOR));
        return "testador/list";
    }

    @GetMapping("/novo")
    public String novoTestador(Model model) {
        model.addAttribute("testador", new Usuario());
        return "testador/form";
    }

    @PostMapping
    public String salvarTestador(@ModelAttribute Usuario testador) {
        testador.setPapel(Papel.TESTADOR);
        service.salvar(testador);
        return "redirect:/testadores";
    }

    @GetMapping("/editar/{id}")
    public String editarTestador(@PathVariable Long id, Model model) {
        Usuario testador = service.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("testador", testador);
        return "testador/form";
    }

    @PostMapping("/editar/{id}")
    public String atualizarTestador(@PathVariable Long id, @ModelAttribute Usuario testadorAtualizado) {
        testadorAtualizado.setId_usuario(id);
        testadorAtualizado.setPapel(Papel.TESTADOR);
        service.salvar(testadorAtualizado);
        return "redirect:/testadores";
    }

    @GetMapping("/excluir/{id}")
    public String excluirTestador(@PathVariable Long id) {
        service.excluir(id);
        return "redirect:/testadores";
    }
}
