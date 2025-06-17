package br.ufscar.dc.dsw.Projeto2DSW.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
public class Projeto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_projeto;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private String descricao;

    @CreationTimestamp
    private Timestamp data_criacao;

    @OneToMany(mappedBy = "projeto")
    private List<SessaoTeste> sessoes;

    @ManyToMany
    @JoinTable(
            name = "projeto_usuarios",
            joinColumns = @JoinColumn(name = "projeto_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")

    )
    private List<Usuario> usuarios;

    public Long getId_projeto() {
        return id_projeto;
    }

    public void setId_projeto(Long id_projeto) {
        this.id_projeto = id_projeto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public List<SessaoTeste> getSessoes() {
        return sessoes;
    }

    public void setSessoes(List<SessaoTeste> sessoes) {
        this.sessoes = sessoes;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
