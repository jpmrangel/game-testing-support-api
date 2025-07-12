package br.ufscar.dc.dsw.Projeto2DSW.controller;

import br.ufscar.dc.dsw.Projeto2DSW.dto.UsuarioCreateDTO;
import br.ufscar.dc.dsw.Projeto2DSW.dto.UsuarioResponseDTO;
import br.ufscar.dc.dsw.Projeto2DSW.model.Papel;
import br.ufscar.dc.dsw.Projeto2DSW.model.Usuario;
import br.ufscar.dc.dsw.Projeto2DSW.service.UserService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService service;


    @GetMapping("/testadores")
    public ResponseEntity<List<UsuarioResponseDTO>> listarTestadores() {
        List<Usuario> testadores = service.listarPorPapel(Papel.TESTADOR);
        List<UsuarioResponseDTO> testadoresDTOs = testadores.stream()
                .map(UsuarioResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(testadoresDTOs);
    }

    @PostMapping("/testadores")
    public ResponseEntity<UsuarioResponseDTO> criarTestador(@Valid @RequestBody UsuarioCreateDTO dto) {
        try {
            Usuario usuario = new Usuario();
            usuario.setNome(dto.getNome());
            usuario.setEmail(dto.getEmail());
            usuario.setSenha(dto.getSenha());
            usuario.setPapel(Papel.TESTADOR);
            
            Usuario novoUsuario = service.salvar(usuario);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(novoUsuario.getId_usuario()).toUri();

            return ResponseEntity.created(location).body(new UsuarioResponseDTO(novoUsuario));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/testadores/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizarTestador(@PathVariable Long id, @Valid @RequestBody UsuarioCreateDTO dto) {
        try {
            return service.buscarPorId(id)
                .map(existingUser -> {
                    existingUser.setNome(dto.getNome());
                    existingUser.setEmail(dto.getEmail());
                    if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
                        existingUser.setSenha(dto.getSenha());
                    }
                    existingUser.setPapel(Papel.TESTADOR);

                    Usuario usuarioAtualizado = service.salvar(existingUser);
                    return ResponseEntity.ok(new UsuarioResponseDTO(usuarioAtualizado));
                })
                .orElse(ResponseEntity.notFound().build()); 
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/administradores")
    public ResponseEntity<List<UsuarioResponseDTO>> listarAdministradores() {
        List<Usuario> administradores = service.listarPorPapel(Papel.ADMINISTRADOR);
        List<UsuarioResponseDTO> administradoresDTO = administradores.stream()
                .map(UsuarioResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(administradoresDTO);
    }

    @PostMapping("/administradores")
    public ResponseEntity<UsuarioResponseDTO> criarAdministrador(@Valid @RequestBody UsuarioCreateDTO dto) {
        try {
            Usuario usuario = new Usuario();
            usuario.setNome(dto.getNome());
            usuario.setEmail(dto.getEmail());
            usuario.setSenha(dto.getSenha());
            usuario.setPapel(Papel.ADMINISTRADOR);
            
            Usuario novoAdmin = service.salvar(usuario);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(novoAdmin.getId_usuario()).toUri();

            return ResponseEntity.created(location).body(new UsuarioResponseDTO(novoAdmin));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/administradores/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizarAdmin(@PathVariable Long id,  @Valid @RequestBody UsuarioCreateDTO dto) {
        try {
            return service.buscarPorId(id)
                .map(existingAdmin -> {
                    existingAdmin.setNome(dto.getNome());
                    existingAdmin.setEmail(dto.getEmail());
                    if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
                        existingAdmin.setSenha(dto.getSenha());
                    }
                    existingAdmin.setPapel(Papel.ADMINISTRADOR);
                    Usuario usuarioAtualizado = service.salvar(existingAdmin);
                    return ResponseEntity.ok(new UsuarioResponseDTO(usuarioAtualizado));
                })
                .orElse(ResponseEntity.notFound().build());
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioResponseDTO> getUsuarioPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(usuario -> ResponseEntity.ok(new UsuarioResponseDTO(usuario)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Long id) {
        try {
            service.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (RuntimeException e) {
            if ("error.user.not.found".equals(e.getMessage())) {
                return ResponseEntity.notFound().build();
            }
            throw e;
        }
    }
}