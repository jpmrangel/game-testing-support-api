
package br.ufscar.dc.dsw.Projeto2DSW.controller;

import br.ufscar.dc.dsw.Projeto2DSW.model.Usuario;
import br.ufscar.dc.dsw.Projeto2DSW.model.Papel;
import br.ufscar.dc.dsw.Projeto2DSW.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService service;

    @GetMapping("/home")
    public String adminHomePage() {
        return "admin/home";
    }

    @GetMapping("/testadores")
    public String listarTestadores(Model model) {
        model.addAttribute("usuarios", service.listarPorPapel(Papel.TESTADOR));
        return "testador/list";
    }

    @GetMapping("/testador/novo")
    public String novoTestador(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "testador/form";
    }

    @PostMapping("/testador")
    public String salvarTestador(@ModelAttribute Usuario usuario) {
        usuario.setPapel(Papel.TESTADOR);
        service.salvar(usuario);
        return "redirect:/admin/testadores";
    }

    @GetMapping("/testador/editar/{id}")
    public String editarTestador(@PathVariable Long id, Model model) {
        Usuario testador = service.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("usuario", testador);
        return "testador/form";
    }

    @PostMapping("/testador/editar/{id}")
    public String atualizarTestador(@PathVariable Long id, @ModelAttribute Usuario testadorAtualizado) {
        testadorAtualizado.setId_usuario(id);
        testadorAtualizado.setPapel(Papel.TESTADOR);
        service.salvar(testadorAtualizado);
        return "redirect:/admin/testadores";
    }

    @GetMapping("/testador/excluir/{id}")
    public String excluirTestador(@PathVariable Long id) {
        service.excluir(id);
        return "redirect:/admin/testadores";
    }


    @GetMapping("/administradores")
    public String listarAdministradores(Model model) {
        model.addAttribute("usuarios", service.listarPorPapel(Papel.ADMINISTRADOR));
        return "admin/list";
    }

    @GetMapping("/administrador/novo")
    public String novoAdministrador(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "admin/form";
    }

    @PostMapping("/administrador")
    public String salvarAdministrador(@ModelAttribute Usuario usuario) {
        usuario.setPapel(Papel.ADMINISTRADOR);
        service.salvar(usuario);
        return "redirect:/admin/administradores";
    }

    @GetMapping("/administrador/editar/{id}")
    public String editarAdmin(@PathVariable Long id, Model model) {
        Usuario admin = service.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("usuario", admin);
        return "admin/form";
    }

    @PostMapping("/administrador/editar/{id}")
    public String atualizarAdmin(@PathVariable Long id, @ModelAttribute Usuario adminAtualizado) {
        adminAtualizado.setId_usuario(id);
        adminAtualizado.setPapel(Papel.ADMINISTRADOR);
        service.salvar(adminAtualizado);
        return "redirect:/admin/administradores";
    }

    @GetMapping("/administrador/excluir/{id}")
    public String excluirAdministrador(@PathVariable Long id) {
        service.excluir(id);
        return "redirect:/admin/administradores";
    }
}
