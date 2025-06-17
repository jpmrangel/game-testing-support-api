package br.ufscar.dc.dsw.Projeto2DSW.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PerfilController {
    @GetMapping("/perfil")
    public String perfil(@AuthenticationPrincipal UserDetails usuario, Model model) {
        model.addAttribute("usuario", usuario);
        return "perfil";
    }
}
