package br.ufscar.dc.dsw.Projeto2DSW.dto;

public class SessaoCreateDTO {
    private Long projetoId;
    private Long estrategiaId;  // Agora usando ID em vez de String
    private Integer tempo;
    private String descricao;

    // Getters e Setters
    public Long getProjetoId() { return projetoId; }
    public void setProjetoId(Long projetoId) { this.projetoId = projetoId; }
    public Long getEstrategiaId() { return estrategiaId; }
    public void setEstrategiaId(Long estrategiaId) { this.estrategiaId = estrategiaId; }
    public Integer getTempo() { return tempo; }
    public void setTempo(Integer tempo) { this.tempo = tempo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}