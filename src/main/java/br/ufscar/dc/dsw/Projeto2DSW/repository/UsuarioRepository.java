package br.ufscar.dc.dsw.Projeto2DSW.repository;

import br.ufscar.dc.dsw.Projeto2DSW.model.Papel;
import br.ufscar.dc.dsw.Projeto2DSW.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    UserDetails findByEmail(String email);
    List<Usuario> findByPapel(Papel papel);
}
