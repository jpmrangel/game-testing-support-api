package br.ufscar.dc.dsw.Projeto2DSW.controller;

import br.ufscar.dc.dsw.Projeto2DSW.model.Estrategia;
import br.ufscar.dc.dsw.Projeto2DSW.model.Imagem;
import br.ufscar.dc.dsw.Projeto2DSW.repository.EstrategiaRepository;
import br.ufscar.dc.dsw.Projeto2DSW.repository.ImagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/estrategias")
public class EstrategiaAdminController {

    @Autowired
    private EstrategiaRepository estrategiaRepository;

    @Autowired
    private ImagemRepository imagemRepository;

    // GET /api/admin/estrategias
    @GetMapping
    public ResponseEntity<List<Estrategia>> listarEstrategias() {
        List<Estrategia> lista = estrategiaRepository.findAll();
        return ResponseEntity.ok(lista);
    }

    // GET /api/admin/estrategias/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Estrategia> buscarEstrategiaPorId(@PathVariable Long id) {
        Optional<Estrategia> estrategiaOpt = estrategiaRepository.findById(id);
        if (estrategiaOpt.isPresent()) {
            return ResponseEntity.ok(estrategiaOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /api/admin/estrategias
    @PostMapping
    public ResponseEntity<Estrategia> criarEstrategia(@RequestBody Estrategia estrategia) {
        if (estrategia.getImagens() != null) {
            for (Imagem imagem : estrategia.getImagens()) {
                imagem.setEstrategia(estrategia);
            }
        }
        Estrategia salva = estrategiaRepository.save(estrategia);
        return ResponseEntity.status(201).body(salva);
    }

    // PUT /api/admin/estrategias/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Estrategia> editarEstrategia(@PathVariable Long id, @RequestBody Estrategia estrategiaAtualizada) {
        Optional<Estrategia> estrategiaOpt = estrategiaRepository.findById(id);
        if (estrategiaOpt.isPresent()) {
            Estrategia estrategia = estrategiaOpt.get();
            estrategia.setNome(estrategiaAtualizada.getNome());
            estrategia.setDescricao(estrategiaAtualizada.getDescricao());
            estrategia.setExemplo(estrategiaAtualizada.getExemplo());
            estrategia.setDica(estrategiaAtualizada.getDica());
            Estrategia salva = estrategiaRepository.save(estrategia);
            return ResponseEntity.ok(salva);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/admin/estrategias/{id}
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
