package br.ufscar.dc.dsw.Projeto2DSW.controller;

import br.ufscar.dc.dsw.Projeto2DSW.dto.ProjetoDTO;
import br.ufscar.dc.dsw.Projeto2DSW.model.Projeto;
import br.ufscar.dc.dsw.Projeto2DSW.model.Usuario;
import br.ufscar.dc.dsw.Projeto2DSW.repository.ProjetoRepository;
import br.ufscar.dc.dsw.Projeto2DSW.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/projetos")
@PreAuthorize("hasRole('ADMIN')")
public class ProjetoAdminController {

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // GET /api/admin/projetos
    @GetMapping
    public ResponseEntity<List<Projeto>> listarProjetos() {
        List<Projeto> projetos = projetoRepository.findAll();
        return ResponseEntity.ok(projetos);
    }

    // GET /api/admin/projetos/ordenados
    @GetMapping("/ordenados")
    public ResponseEntity<List<ProjetoDTO>> listarProjetosOrdenados(
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String direction) {

        Sort.Direction sortDirection = Sort.Direction.ASC;
        if (direction != null && direction.equalsIgnoreCase("desc")) {
            sortDirection = Sort.Direction.DESC;
        }

        Sort sortBy = sort != null ?
                (sort.equalsIgnoreCase("nome") ? Sort.by(sortDirection, "nome") :
                        sort.equalsIgnoreCase("data") ? Sort.by(sortDirection, "dataCriacao") :
                                Sort.by(Sort.Direction.ASC, "nome")) :
                Sort.by(Sort.Direction.ASC, "nome");

        List<Projeto> projetos = projetoRepository.findAll(sortBy);
        List<ProjetoDTO> projetosDTO = projetos.stream()
                .map(ProjetoDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(projetosDTO);
    }

    // GET /api/admin/projetos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Projeto> buscarProjeto(@PathVariable Long id) {
        Optional<Projeto> projetoOpt = projetoRepository.findById(id);
        if (projetoOpt.isPresent()) {
            return ResponseEntity.ok(projetoOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private void pegarIds(Projeto projeto) {
        List<Long> ids = new ArrayList<>();
        for (Usuario usuario : projeto.getUsuarios()) {
            ids.add(usuario.getId_usuario());
        }
        List<Usuario> usuarios = usuarioRepository.findAllById(ids);
        projeto.setUsuarios(usuarios);
    }

    // POST /api/admin/projetos
    @PostMapping
    public ResponseEntity<Projeto> criarProjeto(@RequestBody Projeto projeto) {
        if (projeto.getUsuarios() != null) {
            pegarIds(projeto);
        }

        Projeto salvo = projetoRepository.save(projeto);
        return ResponseEntity.status(201).body(salvo);
    }

    // PUT /api/admin/projetos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Projeto> atualizarProjeto(@PathVariable Long id, @RequestBody Projeto projetoAtualizado) {
        Optional<Projeto> optionalProjeto = projetoRepository.findById(id);
        if (optionalProjeto.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Projeto projeto = optionalProjeto.get();
        projeto.setNome(projetoAtualizado.getNome());
        projeto.setDescricao(projetoAtualizado.getDescricao());

        if (projetoAtualizado.getUsuarios() != null) {
            pegarIds(projetoAtualizado);
            projeto.setUsuarios(projetoAtualizado.getUsuarios());
        } else {
            projeto.setUsuarios(new ArrayList<>());
        }

        Projeto salvo = projetoRepository.save(projeto);
        return ResponseEntity.ok(salvo);
    }

    // DELETE /api/admin/projetos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirProjeto(@PathVariable Long id) {
        if (!projetoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        try {
            projetoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(409).build();
        }
    }
}