package br.ufscar.dc.dsw.Projeto2DSW.controller;

import br.ufscar.dc.dsw.Projeto2DSW.dto.SessaoTesteDTO;
import br.ufscar.dc.dsw.Projeto2DSW.model.SessaoTeste;
import br.ufscar.dc.dsw.Projeto2DSW.model.Usuario;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/testador")
public class TestadorController {

    @Autowired
    private SessaoTeste sessaoService;

    @Autowired
    private ProjetoTestadorController projetoTestadorController;

    @Autowired
    private EstrategiaAdminController estrategiaService;


    @GetMapping("/sessoesTeste")
    public String listarMinhasSessoes(Model model, @AuthenticationPrincipal Usuario testador) {
        model.addAttribute("listaSessoes", sessaoService.findSessoesByTestador(testador));
        return "testador/sessoesTeste/lista";
    }

    @GetMapping("/sessoesTeste/nova")
    public String exibirFormularioSessao(Model model, @AuthenticationPrincipal Usuario testador) {
        model.addAttribute("sessaoDTO", new SessaoTesteDTO());
        model.addAttribute("listaProjetos", projetoS.findProjetosByTestador(testador));
        model.addAttribute("listaEstrategias", estrategiaService.findAll());
        return "testador/sessoesTeste/formulario";
    }

    @PostMapping("/sessoesTeste/salvar")
    public String salvarSessao(@Valid @ModelAttribute("sessaoDTO") SessaoTesteDTO sessaoDTO,
                               BindingResult result,
                               RedirectAttributes attr,
                               @AuthenticationPrincipal Usuario testador,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("listaProjetos", projetoService.findProjetosByTestador(testador));
            model.addAttribute("listaEstrategias", estrategiaService.findAll());
            return "testador/sessoesTeste/formulario";
        }

        try {
            sessaoService.criarSessao(sessaoDTO, testador);
            attr.addFlashAttribute("sucesso", "Sessão de teste criada com sucesso!");
        } catch (Exception e) {
            attr.addFlashAttribute("erro", "Erro ao criar a sessão: " + e.getMessage());
        }
        return "redirect:/testador/sessoesTeste";
    }

    @GetMapping("/sessoesTeste/{id}")
    public String detalhesSessao(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal Usuario testador) {
        // Busca a sessão garantindo que ela pertence ao usuário logado
        SessaoTeste sessao = sessaoService.findByIdAndTestador(id, testador)
                .orElseThrow(() -> new AccessDeniedException("Acesso negado ou sessão não encontrada."));

        model.addAttribute("sessao", sessao);
        return "testador/sessoesTeste/detalhe";
    }

    @PostMapping("/sessoesTeste/{id}/iniciar")
    public String iniciarSessao(@PathVariable("id") Long id, RedirectAttributes attr, @AuthenticationPrincipal Usuario testador) {
        try {
            sessaoService.iniciarSessao(id, testador);
            // Redireciona para a tela de detalhes para começar o teste
            return "redirect:/testador/sessoesTeste/" + id;
        } catch (Exception e) {
            attr.addFlashAttribute("erro", "Não foi possível iniciar a sessão: " + e.getMessage());
            return "redirect:/testador/sessoesTeste";
        }
    }

    @PostMapping("/sessoesTeste/{id}/finalizar")
    public String finalizarSessao(@PathVariable("id") Long id, RedirectAttributes attr, @AuthenticationPrincipal Usuario testador) {
        try {
            sessaoService.finalizarSessao(id, testador);
            attr.addFlashAttribute("sucesso", "Sessão finalizada com sucesso!");
        } catch (Exception e) {
            attr.addFlashAttribute("erro", "Não foi possível finalizar a sessão: " + e.getMessage());
        }
        return "redirect:/testador/sessoesTeste/" + id;
    }
}