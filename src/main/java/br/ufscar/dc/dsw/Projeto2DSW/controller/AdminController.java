package br.ufscar.dc.dsw.Projeto2DSW.controller;

import br.ufscar.dc.dsw.Projeto2DSW.model.Papel;
import br.ufscar.dc.dsw.Projeto2DSW.model.Usuario;
import br.ufscar.dc.dsw.Projeto2DSW.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService service;


    @GetMapping("/testadores")
    public ResponseEntity<List<Usuario>> listarTestadores() {
        List<Usuario> testadores = service.listarPorPapel(Papel.TESTADOR);
        return ResponseEntity.ok(testadores);
    }

    @PostMapping("/testadores")
    public ResponseEntity<Usuario> criarTestador(@RequestBody Usuario usuario) {
        try {
            usuario.setPapel(Papel.TESTADOR);
            Usuario novoUsuario = service.salvar(usuario);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(novoUsuario.getId_usuario()).toUri();

            return ResponseEntity.created(location).body(novoUsuario);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/testadores/{id}")
    public ResponseEntity<Usuario> atualizarTestador(@PathVariable Long id, @RequestBody Usuario usuario) {
        return service.buscarPorId(id)
                .map(existingUser -> {
                    usuario.setId_usuario(id);
                    usuario.setPapel(Papel.TESTADOR);
                    Usuario usuarioAtualizado = service.salvar(usuario);
                    return ResponseEntity.ok(usuarioAtualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/administradores")
    public ResponseEntity<List<Usuario>> listarAdministradores() {
        List<Usuario> administradores = service.listarPorPapel(Papel.ADMINISTRADOR);
        return ResponseEntity.ok(administradores);
    }

    @PostMapping("/administradores")
    public ResponseEntity<Usuario> criarAdministrador(@RequestBody Usuario usuario) {
        try {
            usuario.setPapel(Papel.ADMINISTRADOR);
            Usuario novoAdmin = service.salvar(usuario);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(novoAdmin.getId_usuario()).toUri();

            return ResponseEntity.created(location).body(novoAdmin);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/administradores/{id}")
    public ResponseEntity<?> atualizarAdmin(@PathVariable Long id, @RequestBody Usuario adminAtualizado) {
        // Primeiro, verifica se o administrador a ser atualizado existe
        return service.buscarPorId(id)
                .map(existingAdmin -> {
                    adminAtualizado.setId_usuario(id);
                    adminAtualizado.setPapel(Papel.ADMINISTRADOR);

                    try {
                        Usuario usuarioAtualizado = service.salvar(adminAtualizado);
                        return ResponseEntity.ok(usuarioAtualizado);
                    } catch (DataIntegrityViolationException e) {

                        return ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .body("Erro: O e-mail informado já está em uso por outro usuário.");
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> getUsuarioPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // No AdminController.java

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