package br.ufscar.dc.dsw.Projeto2DSW.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

    @GetMapping("/logout-message")
    public String logoutMessage() {
        return "logout-message";
    }
}
