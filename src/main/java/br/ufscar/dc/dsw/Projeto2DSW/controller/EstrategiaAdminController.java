package br.ufscar.dc.dsw.Projeto2DSW.controller;

import br.ufscar.dc.dsw.Projeto2DSW.dto.EstrategiaDTO;
import br.ufscar.dc.dsw.Projeto2DSW.model.Estrategia;
import br.ufscar.dc.dsw.Projeto2DSW.model.Imagem;
import br.ufscar.dc.dsw.Projeto2DSW.repository.EstrategiaRepository;
import br.ufscar.dc.dsw.Projeto2DSW.repository.ImagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/estrategias")
public class EstrategiaAdminController {

    @Autowired
    private EstrategiaRepository estrategiaRepository;

    @Autowired
    private ImagemRepository imagemRepository;

    @GetMapping
    public ResponseEntity<List<EstrategiaDTO>> listarEstrategias() {
        List<Estrategia> estrategias = estrategiaRepository.findAll();
        List<EstrategiaDTO> estrategiaDTOs = estrategias.stream()
                .map(EstrategiaDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(estrategiaDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstrategiaDTO> buscarEstrategiaPorId(@PathVariable Long id) {
        return estrategiaRepository.findById(id)
                .map(estrategia -> ResponseEntity.ok(new EstrategiaDTO(estrategia)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EstrategiaDTO> criarEstrategia(@RequestBody Estrategia estrategia) {
        if (estrategia.getImagens() != null) {
            for (Imagem imagem : estrategia.getImagens()) {
                imagem.setEstrategia(estrategia);
            }
        }
        Estrategia salva = estrategiaRepository.save(estrategia);
        EstrategiaDTO salvaDTO = new EstrategiaDTO(salva);
        return ResponseEntity.status(201).body(salvaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstrategiaDTO> editarEstrategia(@PathVariable Long id, @RequestBody Estrategia estrategiaAtualizada) {
        return estrategiaRepository.findById(id)
                .map(estrategia -> {
                    estrategia.setNome(estrategiaAtualizada.getNome());
                    estrategia.setDescricao(estrategiaAtualizada.getDescricao());
                    estrategia.setExemplo(estrategiaAtualizada.getExemplo());
                    estrategia.setDica(estrategiaAtualizada.getDica());
                    
                    Estrategia salva = estrategiaRepository.save(estrategia);
                    return ResponseEntity.ok(new EstrategiaDTO(salva));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirEstrategia(@PathVariable Long id) {
        Optional<Estrategia> estrategiaOpt = estrategiaRepository.findById(id);
        if (estrategiaOpt.isPresent()) {
            List<Imagem> imagens = imagemRepository.findByEstrategiaIdEstrategia(id);
            imagemRepository.deleteAll(imagens);
            estrategiaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
