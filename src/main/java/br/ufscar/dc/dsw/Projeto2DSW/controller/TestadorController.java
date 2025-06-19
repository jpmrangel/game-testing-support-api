//package br.ufscar.dc.dsw.Projeto2DSW.controller;
//import br.ufscar.dc.dsw.Projeto2DSW.model.Usuario;
//import br.ufscar.dc.dsw.Projeto2DSW.service.EstrategiaService;
//import br.ufscar.dc.dsw.Projeto2DSW.service.ProjetoService;
//import br.ufscar.dc.dsw.Projeto2DSW.service.SessaoTesteService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequestMapping("/testador")
//public class TestadorController {
//
//    @Autowired
//    private SessaoTesteService sessaoService;
//
//    @Autowired
//    private ProjetoService projetoService;
//
//    @Autowired
//    private EstrategiaService estrategiaService;
//
//    @GetMapping
//    public String dashboard(Model model, @AuthenticationPrincipal Usuario testador) {
//        Long id = testador.getId_usuario();
//
//        model.addAttribute("totalSessoes", sessaoService.contarPorTestado(id));
//        model.addAttribute("projetos", projetoService.listarPorTestador(id));
//        model.addAttribute("estrategias", estrategiaService.listarTodas());
//
//        return "testador/home";
//    }
//}
