package br.ufscar.dc.dsw.Projeto2DSW.infra.seed;

import br.ufscar.dc.dsw.Projeto2DSW.model.Papel;
import br.ufscar.dc.dsw.Projeto2DSW.model.Usuario;
import br.ufscar.dc.dsw.Projeto2DSW.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class AdminInicial implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (usuarioRepository.findByEmail("admin@teste.com") == null) {
            Usuario admin = new Usuario();
            admin.setNome("Administrador");
            admin.setEmail("admin@teste.com");
            admin.setSenha(passwordEncoder.encode("admin"));
            admin.setPapel(Papel.ADMINISTRADOR);
            admin.setData_criacao(new Timestamp(System.currentTimeMillis()));

            usuarioRepository.save(admin);
            System.out.println("Usuário administrador criado com sucesso: " + admin.getEmail());
        } else {
            System.out.println("Usuário administrador já existe.");
        }
    }
}
