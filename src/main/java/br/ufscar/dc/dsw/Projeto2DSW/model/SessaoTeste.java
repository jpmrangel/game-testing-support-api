package br.ufscar.dc.dsw.Projeto2DSW.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
public class SessaoTeste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_sessao;

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

    public Long getId_sessao() {
        return id_sessao;
    }

    public void setId_sessao(Long id_sessao) {
        this.id_sessao = id_sessao;
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
