package br.ufscar.dc.dsw.Projeto2DSW.dto;

import jakarta.validation.constraints.NotBlank;

public class BugCreateDTO {

    @NotBlank(message = "A descrição do bug não pode estar em branco.")
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}