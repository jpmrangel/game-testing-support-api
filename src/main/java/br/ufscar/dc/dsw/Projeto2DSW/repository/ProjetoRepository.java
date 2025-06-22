package br.ufscar.dc.dsw.Projeto2DSW.repository;

import br.ufscar.dc.dsw.Projeto2DSW.model.Projeto;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
    List<Projeto> findByUsuarios_IdUsuario(Long idUsuario);

    List<Projeto> findByUsuarios_IdUsuario(Long idUsuario, Sort sort);

}
