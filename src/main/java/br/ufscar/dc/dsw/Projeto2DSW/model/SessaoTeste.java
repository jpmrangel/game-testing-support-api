package br.ufscar.dc.dsw.Projeto2DSW.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
public class SessaoTeste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSessao;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private String nome_testador;

    @Column(nullable = false)
    private int tempo;

    @Enumerated(EnumType.STRING)
    private Status status;

    @CreationTimestamp
    private Timestamp data_criacao;

    @CreationTimestamp
    private Timestamp data_inicio;

    @CreationTimestamp
    private Timestamp data_fim;

    @ManyToOne
    @JoinColumn(name = "estrategia_id")
    private Estrategia estrategia;

    @ManyToOne
    @JoinColumn(name = "projeto_id")
    private Projeto projeto;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "sessao")
    private List<Bug> bugs;

    public Long getIdSessao() {
        return idSessao;
    }

    public void setIdSessao(Long idSessao) {
        this.idSessao = idSessao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome_testador() {
        return nome_testador;
    }

    public void setNome_testador(String nome_testador) {
        this.nome_testador = nome_testador;
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Timestamp getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(Timestamp data_criacao) {
        this.data_criacao = data_criacao;
    }

    public Timestamp getData_inicio() {
        return data_inicio;
    }

    public void setData_inicio(Timestamp data_inicio) {
        this.data_inicio = data_inicio;
    }

    public Timestamp getData_fim() {
        return data_fim;
    }

    public void setData_fim(Timestamp data_fim) {
        this.data_fim = data_fim;
    }

    public Estrategia getEstrategia() {
        return estrategia;
    }

    public void setEstrategia(Estrategia estrategia) {
        this.estrategia = estrategia;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Bug> getBugs() {
        return bugs;
    }

    public void setBugs(List<Bug> bugs) {
        this.bugs = bugs;
    }
}
