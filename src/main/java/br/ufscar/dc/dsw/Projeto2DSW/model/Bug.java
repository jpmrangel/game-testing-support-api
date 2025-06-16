package br.ufscar.dc.dsw.Projeto2DSW.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
public class Bug {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_bug;

    private String descricao;

    @CreationTimestamp
    private Timestamp data_criacao;

    @ManyToOne
    @JoinColumn(name = "sessao_id")
    private SessaoTeste sessao;
}
