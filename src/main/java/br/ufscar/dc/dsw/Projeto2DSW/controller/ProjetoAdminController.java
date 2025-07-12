package br.ufscar.dc.dsw.Projeto2DSW.controller;

import br.ufscar.dc.dsw.Projeto2DSW.dto.ProjetoCreateDTO;
import br.ufscar.dc.dsw.Projeto2DSW.dto.ProjetoDTO;
import br.ufscar.dc.dsw.Projeto2DSW.model.Projeto;
import br.ufscar.dc.dsw.Projeto2DSW.model.Usuario;
import br.ufscar.dc.dsw.Projeto2DSW.repository.ProjetoRepository;
import br.ufscar.dc.dsw.Projeto2DSW.repository.UsuarioRepository;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/projetos")
@PreAuthorize("hasRole('ADMIN')")
public class ProjetoAdminController {

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<List<ProjetoDTO>> listarProjetos() {
        List<Projeto> projetos = projetoRepository.findAll();
        List<ProjetoDTO> projetosDTO = projetos.stream()
            .map(ProjetoDTO::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(projetosDTO);
    }

    @GetMapping("/ordenados")
    public ResponseEntity<List<ProjetoDTO>> listarProjetosOrdenados(
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String direction) {

        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

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

    @GetMapping("/{id}")
    public ResponseEntity<ProjetoDTO> buscarProjeto(@PathVariable Long id) {
        return projetoRepository.findById(id)
            .map(projeto -> ResponseEntity.ok(new ProjetoDTO(projeto)))
            .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping
    public ResponseEntity<ProjetoDTO> criarProjeto(@Valid @RequestBody ProjetoCreateDTO dto) {
        Projeto projeto = new Projeto();
        projeto.setNome(dto.getNome());
        projeto.setDescricao(dto.getDescricao());

        if (dto.getMembrosIds() != null && !dto.getMembrosIds().isEmpty()) {
            List<Usuario> usuarios = usuarioRepository.findAllById(dto.getMembrosIds());
            projeto.setUsuarios(usuarios);
        }

        Projeto salvo = projetoRepository.save(projeto);
        return ResponseEntity.status(201).body(new ProjetoDTO(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjetoDTO> atualizarProjeto(@PathVariable Long id, @Valid @RequestBody ProjetoCreateDTO dto) {
        return projetoRepository.findById(id)
            .map(projeto -> {
                projeto.setNome(dto.getNome());
                projeto.setDescricao(dto.getDescricao());

                if (dto.getMembrosIds() != null) {
                    if (dto.getMembrosIds().isEmpty()) {
                        projeto.setUsuarios(new ArrayList<>());
                    } else {
                        List<Usuario> usuarios = usuarioRepository.findAllById(dto.getMembrosIds());
                        projeto.setUsuarios(usuarios);
                    }
                }

                Projeto salvo = projetoRepository.save(projeto);
                return ResponseEntity.ok(new ProjetoDTO(salvo));
            })
            .orElse(ResponseEntity.notFound().build());
    }

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