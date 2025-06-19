package br.ufscar.dc.dsw.Projeto2DSW.controller;

import br.ufscar.dc.dsw.Projeto2DSW.model.Papel;
import br.ufscar.dc.dsw.Projeto2DSW.model.Usuario;
import br.ufscar.dc.dsw.Projeto2DSW.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/usuarios")
public class AdminController {

    @Autowired
    private UserService usuarioService;


    @GetMapping("/novo-admin")
    public String novoAdmin(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "admin/usuarios/formAdmin";
    }


    @GetMapping("/novo-testador")
    public String novoTestador(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "admin/usuarios/formTestador";
    }

    @PostMapping("/salvar-admin")
    public String salvarAdmin(@ModelAttribute Usuario usuario) {
        usuario.setPapel(Papel.ADMINISTRADOR);
        usuarioService.salvar(usuario);
        return "redirect:/admin/usuarios/admins";
    }


    @PostMapping("/salvar-testador")
    public String salvarTestador(@ModelAttribute Usuario usuario) {
        usuario.setPapel(Papel.TESTADOR);
        usuarioService.salvar(usuario);
        return "redirect:/admin/usuarios/testadores";
    }


    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.buscarPorId(id);
        if (usuario != null) {
            model.addAttribute("usuario", usuario);
            if (usuario.getPapel() == Papel.ADMINISTRADOR) {
                return "admin/usuarios/formAdmin";
            } else {
                return "admin/usuarios/formTestador";
            }
        }
        return "redirect:/admin/usuarios";
    }

    @PostMapping("/atualizar")
    public String atualizarUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.salvar(usuario);
        if (usuario.getPapel() == Papel.ADMINISTRADOR) {
            return "redirect:/admin/usuarios/admins";
        } else {
            return "redirect:/admin/usuarios/testadores";
        }
    }

    @GetMapping("/remover/{id}")
    public String removerUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        if (usuario != null) {
            Papel papel = usuario.getPapel();
            usuarioService.remover(id);
            return papel == Papel.ADMINISTRADOR
                    ? "redirect:/admin/usuarios/admins"
                    : "redirect:/admin/usuarios/testadores";
        }
        return "redirect:/admin/usuarios";
    }
}
