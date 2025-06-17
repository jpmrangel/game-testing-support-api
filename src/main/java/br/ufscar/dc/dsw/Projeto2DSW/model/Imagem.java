package br.ufscar.dc.dsw.Projeto2DSW.model;

import jakarta.persistence.*;

@Entity
public class Imagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_imagem;

    @Column(nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "estrategia_id")
    private Estrategia estrategia;

    public Imagem() {}

    public Imagem(String url) {
        this.url = url;
    }

    public Long getId_imagem() {
        return id_imagem;
    }

    public void setId_imagem(Long id_imagem) {
        this.id_imagem = id_imagem;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Estrategia getEstrategia() {
        return estrategia;
    }

    public void setEstrategia(Estrategia estrategia) {
        this.estrategia = estrategia;
    }
}
