package br.ufscar.dc.dsw.Projeto2DSW.controller;

import br.ufscar.dc.dsw.Projeto2DSW.model.Estrategia;
import br.ufscar.dc.dsw.Projeto2DSW.model.Imagem;
import br.ufscar.dc.dsw.Projeto2DSW.repository.EstrategiaRepository;
import br.ufscar.dc.dsw.Projeto2DSW.repository.ImagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/estrategias")
public class EstrategiaAdminController {

    @Autowired
    private EstrategiaRepository estrategiaRepository;
    @Autowired
    private ImagemRepository imagemRepository;

    @GetMapping("")
    public String estrategias() {
        return "admin/estrategias/estrategias";
    }

    @GetMapping("/listar-estrategias")
    public String listarEstrategias(Model model) {
        model.addAttribute("estrategias", estrategiaRepository.findAll());
        return "admin/estrategias/listar-estrategias";
    }

    @GetMapping("/nova-estrategia")
    public String novaEstrategia(Model model) {
        model.addAttribute("estrategia", new Estrategia());
        return "admin/estrategias/nova-estrategia";
    }

    @PostMapping("/nova-estrategia")
    public String novaEstrategia(@ModelAttribute Estrategia estrategia, @RequestParam(name = "imagensUrls", required = false) List<String> imagensUrls) {
        estrategiaRepository.save(estrategia);

        if (imagensUrls != null) {
            for (String url : imagensUrls) {
                if (url != null && !url.trim().isEmpty()) {
                    Imagem imagem = new Imagem(url);
                    imagem.setEstrategia(estrategia);
                    imagemRepository.save(imagem);
                }
            }
        }

        return "redirect:/admin/estrategias/listar-estrategias";
    }

    @GetMapping("/editar/{id}")
    public String editarEstrategia(@PathVariable("id") Long id, Model model) {
        Estrategia estrategia = estrategiaRepository.findById(id).orElseThrow();
        model.addAttribute("estrategia", estrategia);
        return "admin/estrategias/editar-estrategia";
    }

    @PostMapping("/editar/{id}")
    public String editarEstrategia(@PathVariable Long id, @ModelAttribute Estrategia estrategia, @RequestParam(name = "imagensUrls", required = false) List<String> imagensUrls, @RequestParam(name = "imagensParaExcluir", required = false) List<Long> imagensParaExcluir) {
        Estrategia estrategiaOriginal = estrategiaRepository.findById(id).orElseThrow();

        estrategiaOriginal.setNome(estrategia.getNome());
        estrategiaOriginal.setDescricao(estrategia.getDescricao());
        estrategiaOriginal.setExemplo(estrategia.getExemplo());
        estrategiaOriginal.setDica(estrategia.getDica());

        estrategiaRepository.save(estrategiaOriginal);

        if (imagensParaExcluir != null){
            for(Long imgId : imagensParaExcluir){
                imagemRepository.deleteById(imgId);
            }
        }

        if (imagensUrls != null) {
            for (String url : imagensUrls) {
                if (url != null && !url.trim().isEmpty()) {
                    Imagem imagem = new Imagem();
                    imagem.setUrl(url.trim());
                    imagem.setEstrategia(estrategiaOriginal);
                    imagemRepository.save(imagem);
                }
            }
        }

        return "redirect:/admin/estrategias/listar-estrategias";
    }

    @PostMapping("/excluir/{id}")
    public String excluirEstrategia(@PathVariable Long id) {
        List<Imagem> imagens = imagemRepository.findByEstrategiaIdEstrategia(id);
        imagemRepository.deleteAll(imagens);

        estrategiaRepository.deleteById(id);
        return "redirect:/admin/estrategias/listar-estrategias";
    }
}
