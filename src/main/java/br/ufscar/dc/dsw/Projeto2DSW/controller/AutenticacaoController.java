package br.ufscar.dc.dsw.Projeto2DSW.controller;

import br.ufscar.dc.dsw.Projeto2DSW.infra.security.TokenService;
import br.ufscar.dc.dsw.Projeto2DSW.model.Usuario;
import br.ufscar.dc.dsw.Projeto2DSW.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid Usuario usuario) {
        var senhaUsuario = new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getSenha());
        var authentication = authenticationManager.authenticate(senhaUsuario);

        var token = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(token);
    }
}
