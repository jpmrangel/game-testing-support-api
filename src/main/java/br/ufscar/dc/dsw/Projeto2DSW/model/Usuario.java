package br.ufscar.dc.dsw.Projeto2DSW.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;

    private String nome;
    private String email;
    private String senha;

    @Enumerated(EnumType.STRING)
    private Papel papel;

    @CreationTimestamp
    private Timestamp data_criacao;

    @OneToMany(mappedBy = "usuario")
    private List<SessaoTeste> sessoes;
}
