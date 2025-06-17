package br.ufscar.dc.dsw.Projeto2DSW.infra.security;

import br.ufscar.dc.dsw.Projeto2DSW.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String gerarToken(Usuario usuario) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withSubject(usuario.getEmail())
                    .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token", exception);
        }
    }

    public String validarToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException exception) {
            return "";
        }
    }
}
