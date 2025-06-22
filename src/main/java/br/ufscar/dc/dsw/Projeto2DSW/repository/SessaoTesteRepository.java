package br.ufscar.dc.dsw.Projeto2DSW.repository;

import br.ufscar.dc.dsw.Projeto2DSW.model.SessaoTeste;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessaoTesteRepository extends JpaRepository<SessaoTeste, Long> {
    List<SessaoTeste> findByUsuario_IdUsuario(Long idUsuario);
}