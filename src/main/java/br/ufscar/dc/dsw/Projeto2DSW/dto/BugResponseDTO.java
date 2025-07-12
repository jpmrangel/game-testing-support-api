package br.ufscar.dc.dsw.Projeto2DSW.dto;

import br.ufscar.dc.dsw.Projeto2DSW.model.Bug;

public class BugResponseDTO {
    private Long id;
    private String descricao;

    public BugResponseDTO(Bug bug) {
        this.id = bug.getId_bug();
        this.descricao = bug.getDescricao();
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }
}