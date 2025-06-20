package br.ufscar.dc.dsw.Projeto2DSW.infra.seed;

import br.ufscar.dc.dsw.Projeto2DSW.model.Papel;
import br.ufscar.dc.dsw.Projeto2DSW.model.Usuario;
import br.ufscar.dc.dsw.Projeto2DSW.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class UsuariosIniciais implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {

        if (usuarioRepository.findByEmail("admin@teste.com").isEmpty()) {
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

        if (usuarioRepository.findByEmail("testador@teste.com").isEmpty()) {
            Usuario testador = new Usuario();
            testador.setNome("Testador");
            testador.setEmail("testador@teste.com");
            testador.setSenha(passwordEncoder.encode("testador"));
            testador.setPapel(Papel.TESTADOR);
            testador.setData_criacao(new Timestamp(System.currentTimeMillis()));

            usuarioRepository.save(testador);
            System.out.println("Usuário testador criado com sucesso: " + testador.getEmail());
        } else {
            System.out.println("Usuário testador já existe.");
        }
    }
}
