package br.ufscar.dc.dsw.Projeto2DSW.controller;

import br.ufscar.dc.dsw.Projeto2DSW.dto.UsuarioDTO;
import br.ufscar.dc.dsw.Projeto2DSW.model.Papel;
import br.ufscar.dc.dsw.Projeto2DSW.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        model.addAttribute("listaAdmins", userService.findUsersByRole(Papel.ADMINISTRADOR));
        model.addAttribute("listaTesters", userService.findUsersByRole(Papel.TESTADOR));
        return "admin/usuarios/lista";
    }

    @GetMapping("/usuarios/cadastrar")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("usuarioDTO", new UsuarioDTO());
        return "admin/usuarios/formulario";
    }

    @PostMapping("/usuarios/salvar")
    public String salvarUsuario(@Valid @ModelAttribute("usuarioDTO") UsuarioDTO usuarioDTO,
                                BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "admin/usuarios/formulario";
        }

        try {
            userService.criarUsuario(usuarioDTO);
            attr.addFlashAttribute("sucesso", "Usuário salvo com sucesso!");
        } catch (IllegalArgumentException e) {
            attr.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/admin/usuarios";
    }
    @GetMapping("/usuarios/remover/{id}")
    public String removerUsuario(@PathVariable("id") Long id, RedirectAttributes attr) {
        try {
            userService.deleteUser(id);
            attr.addFlashAttribute("sucesso", "Usuário removido com sucesso.");
        } catch (Exception e) {
            attr.addFlashAttribute("erro", "Não foi possível remover o usuário.");
        }
        return "redirect:/admin/usuarios";
    }
}