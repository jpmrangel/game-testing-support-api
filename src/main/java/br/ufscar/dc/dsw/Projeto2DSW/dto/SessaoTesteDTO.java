package br.ufscar.dc.dsw.Projeto2DSW.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SessaoTesteDTO {
    @NotNull(message = "O projeto é obrigatório.")
    private Long projetoId;

    @NotNull(message = "A estratégia é obrigatória.")
    private Long estrategiaId;

    @NotBlank(message = "A descrição não pode estar em branco.")
    private String descricao;

    @NotNull(message = "O tempo da sessão é obrigatório.")
    @Min(value = 1, message = "O tempo da sessão deve ser de no mínimo 1 minuto.")
    private Integer tempo; // Tempo em minutos

    // Getters e Setters
    public Long getProjetoId() { return projetoId; }
    public void setProjetoId(Long projetoId) { this.projetoId = projetoId; }
    public Long getEstrategiaId() { return estrategiaId; }
    public void setEstrategiaId(Long estrategiaId) { this.estrategiaId = estrategiaId; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Integer getTempo() { return tempo; }
    public void setTempo(Integer tempo) { this.tempo = tempo; }
}