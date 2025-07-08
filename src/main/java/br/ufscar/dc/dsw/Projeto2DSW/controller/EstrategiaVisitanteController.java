package br.ufscar.dc.dsw.Projeto2DSW.controller;

import br.ufscar.dc.dsw.Projeto2DSW.dto.EstrategiaDTO;
import br.ufscar.dc.dsw.Projeto2DSW.model.Estrategia;
import br.ufscar.dc.dsw.Projeto2DSW.repository.EstrategiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/estrategias")
public class EstrategiaVisitanteController {

    @Autowired
    EstrategiaRepository estrategiaRepository;

    @GetMapping
    public ResponseEntity<List<EstrategiaDTO>> listarEstrategias() {
        List<Estrategia> estrategias = estrategiaRepository.findAll();
        List<EstrategiaDTO> estrategiasDTO = estrategias.stream()
                .map(EstrategiaDTO::new)
                .toList();
        return ResponseEntity.ok(estrategiasDTO);
    }
}
