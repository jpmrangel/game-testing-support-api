package br.ufscar.dc.dsw.Projeto2DSW.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

public class ProjetoCreateDTO {

    @NotBlank(message = "O nome do projeto é obrigatório.")
    private String nome;

    @NotBlank(message = "A descrição do projeto é obrigatória.")
    private String descricao;

    private List<Long> membrosIds;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public List<Long> getMembrosIds() { return membrosIds; }
    public void setMembrosIds(List<Long> membrosIds) { this.membrosIds = membrosIds; }
}