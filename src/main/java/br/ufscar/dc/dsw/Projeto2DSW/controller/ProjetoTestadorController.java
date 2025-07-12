package br.ufscar.dc.dsw.Projeto2DSW.controller;

import br.ufscar.dc.dsw.Projeto2DSW.dto.ProjetoDTO;
import br.ufscar.dc.dsw.Projeto2DSW.model.Usuario;
import br.ufscar.dc.dsw.Projeto2DSW.service.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/testador/projetos")
public class ProjetoTestadorController {

    @Autowired
    private ProjetoService service;

    @GetMapping
    public ResponseEntity<List<ProjetoDTO>> listarProjetos(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @RequestParam(defaultValue = "nome") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        List<ProjetoDTO> projetosDTO = service.listarPorUsuario(usuarioLogado.getId_usuario(), sort).stream()
                .map(ProjetoDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(projetosDTO);
    }
}