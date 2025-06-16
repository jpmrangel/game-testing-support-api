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

    private String nome;
    private String descricao;

    @CreationTimestamp
    private Timestamp data_criacao;

    @OneToMany(mappedBy = "projeto")
    private List<SessaoTeste> sessoes;
}
