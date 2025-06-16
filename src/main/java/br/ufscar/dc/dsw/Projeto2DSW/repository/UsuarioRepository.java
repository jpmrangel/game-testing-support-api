package br.ufscar.dc.dsw.Projeto2DSW.repository;

import br.ufscar.dc.dsw.Projeto2DSW.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
