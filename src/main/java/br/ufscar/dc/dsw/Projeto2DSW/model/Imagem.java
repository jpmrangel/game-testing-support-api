package br.ufscar.dc.dsw.Projeto2DSW.model;

import jakarta.persistence.*;

@Entity
public class Imagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_imagem;

    private String url;

    @ManyToOne
    @JoinColumn(name = "estrategia_id")
    private Estrategia estrategia;
}
