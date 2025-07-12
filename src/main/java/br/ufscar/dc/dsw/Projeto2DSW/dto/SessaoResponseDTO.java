package br.ufscar.dc.dsw.Projeto2DSW.dto;

import br.ufscar.dc.dsw.Projeto2DSW.model.SessaoTeste;
import br.ufscar.dc.dsw.Projeto2DSW.model.Status;

import java.sql.Timestamp;

public class SessaoResponseDTO {
    private Long idSessao;
    private String nomeTestador;
    private String projetoNome;
    private String estrategiaNome;
    private String descricao;
    private Integer tempo;
    private Status status;
    private Timestamp dataCriacao;

    public SessaoResponseDTO(SessaoTeste sessao) {
        this.idSessao = sessao.getIdSessao();
        this.nomeTestador = sessao.getNome_testador();
        this.projetoNome = sessao.getProjeto() != null ? sessao.getProjeto().getNome() : null;
        this.estrategiaNome = sessao.getEstrategia() != null ? sessao.getEstrategia().getNome() : null;
        this.tempo = sessao.getTempo();
        this.descricao = sessao.getDescricao();
        this.status = sessao.getStatus();
        this.dataCriacao = sessao.getData_criacao();
    }

    // Getters
    public Long getIdSessao() { return idSessao; }
    public String getNomeTestador() { return nomeTestador; }
    public String getProjetoNome() { return projetoNome; }
    public String getEstrategiaNome() { return estrategiaNome; }
    public String getDescricao() { return descricao; }
    public Integer getTempo() { return tempo; }
    public Status getStatus() { return status; }
    public Timestamp getDataCriacao() { return dataCriacao; }
}