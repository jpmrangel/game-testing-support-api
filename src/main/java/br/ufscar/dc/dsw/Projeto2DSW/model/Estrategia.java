package br.ufscar.dc.dsw.Projeto2DSW.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Estrategia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_estrategia;

    private String nome;
    private String descricao;
    private String exemplo;
    private String dica;

    @OneToMany(mappedBy = "estrategia")
    private List<SessaoTeste> sessoes;

    @OneToMany(mappedBy = "estrategia")
    private List<Imagem> imagens;
}
