package br.ufscar.dc.dsw.Projeto2DSW.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/testador")
public class TestadorController {

    @GetMapping("/home")
    public String testadorHomePage() {
        return "/testador/home";
    }
}
