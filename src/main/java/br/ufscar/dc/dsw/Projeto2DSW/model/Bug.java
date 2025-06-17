package br.ufscar.dc.dsw.Projeto2DSW.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
public class Bug {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_bug;

    @Column(nullable = false)
    private String descricao;

    @CreationTimestamp
    private Timestamp data_criacao;

    @ManyToOne
    @JoinColumn(name = "sessao_id")
    private SessaoTeste sessao;

    public Long getId_bug() {
        return id_bug;
    }

    public void setId_bug(Long id_bug) {
        this.id_bug = id_bug;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Timestamp getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(Timestamp data_criacao) {
        this.data_criacao = data_criacao;
    }

    public SessaoTeste getSessao() {
        return sessao;
    }

    public void setSessao(SessaoTeste sessao) {
        this.sessao = sessao;
    }
}
