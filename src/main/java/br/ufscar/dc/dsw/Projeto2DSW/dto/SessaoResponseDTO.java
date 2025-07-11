package br.ufscar.dc.dsw.Projeto2DSW.dto;

import br.ufscar.dc.dsw.Projeto2DSW.model.SessaoTeste;
import br.ufscar.dc.dsw.Projeto2DSW.model.Status;

import java.sql.Timestamp;

public class SessaoResponseDTO {
    private Long idSessao;
    private String nomeTestador;
    private String estrategiaNome;  // Nome da estratégia em vez do objeto
    private Integer tempo;
    private String descricao;
    private Status status;
    private Timestamp dataCriacao;
    private Timestamp dataInicio;
    private Timestamp dataFim;
    private Long projetoId;
    private String projetoNome;

    public SessaoResponseDTO(SessaoTeste sessao) {
        this.idSessao = sessao.getIdSessao();
        this.nomeTestador = sessao.getNome_testador();
        this.estrategiaNome = sessao.getEstrategia() != null ? sessao.getEstrategia().getNome() : null;
        this.tempo = sessao.getTempo();
        this.descricao = sessao.getDescricao();
        this.status = sessao.getStatus();
        this.dataCriacao = sessao.getData_criacao();
        this.dataInicio = sessao.getData_inicio();
        this.dataFim = sessao.getData_fim();
        if (sessao.getProjeto() != null) {
            this.projetoId = sessao.getProjeto().getId_projeto();
            this.projetoNome = sessao.getProjeto().getNome();
        }
    }

    // Getters
    public Long getIdSessao() { return idSessao; }
    public String getNomeTestador() { return nomeTestador; }
    public String getEstrategiaNome() { return estrategiaNome; }
    public Integer getTempo() { return tempo; }
    public String getDescricao() { return descricao; }
    public Status getStatus() { return status; }
    public Timestamp getDataCriacao() { return dataCriacao; }
    public Timestamp getDataInicio() { return dataInicio; }
    public Timestamp getDataFim() { return dataFim; }
    public Long getProjetoId() { return projetoId; }
    public String getProjetoNome() { return projetoNome; }
}