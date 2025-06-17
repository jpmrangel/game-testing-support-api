package br.ufscar.dc.dsw.Projeto2DSW.repository;

import br.ufscar.dc.dsw.Projeto2DSW.model.Imagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagemRepository extends JpaRepository<Imagem, Long> {
    List<Imagem> findByEstrategiaIdEstrategia(Long id);
}
