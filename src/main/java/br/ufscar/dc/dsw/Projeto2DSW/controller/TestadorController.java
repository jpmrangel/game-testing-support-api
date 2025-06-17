package br.ufscar.dc.dsw.Projeto2DSW.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/testador")
public class TestadorController {
    @GetMapping("/home")
    public String home() {
        return "testador/home";
    }
}
