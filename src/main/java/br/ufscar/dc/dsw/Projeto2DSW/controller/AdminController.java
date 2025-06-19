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

    @GetMapping
    public String listarAdmins(Model model) {
        model.addAttribute("admins", service.listarPorPapel(Papel.ADMINISTRADOR));
        return "admin/listarAdmin";
    }

    @GetMapping("/novo")
    public String novoAdmin(Model model) {
        model.addAttribute("admin", new Usuario());
        return "admin/form";
    }

    @PostMapping
    public String salvarAdmin(@ModelAttribute Usuario admin) {
        admin.setPapel(Papel.ADMINISTRADOR);
        service.salvar(admin);
        return "redirect:/admin";
    }

    @GetMapping("/editar/{id}")
    public String editarAdmin(@PathVariable Long id, Model model) {
        Usuario admin = service.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("admin", admin);
        return "admin/form";
    }

    @PostMapping("/editar/{id}")
    public String atualizarAdmin(@PathVariable Long id, @ModelAttribute Usuario adminAtualizado) {
        adminAtualizado.setId_usuario(id);
        adminAtualizado.setPapel(Papel.ADMINISTRADOR);
        service.salvar(adminAtualizado);
        return "redirect:/admin";
    }

    @GetMapping("/excluir/{id}")
    public String excluirAdmin(@PathVariable Long id) {
        service.excluir(id);
        return "redirect:/admin";
    }
}
