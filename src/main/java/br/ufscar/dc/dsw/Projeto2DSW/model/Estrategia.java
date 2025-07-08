package br.ufscar.dc.dsw.Projeto2DSW.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Estrategia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estrategia")
    private Long idEstrategia;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private String exemplo;

    @Column(nullable = false)
    private String dica;

    @OneToMany(mappedBy = "estrategia")
    private List<SessaoTeste> sessoes;

    @OneToMany(mappedBy = "estrategia", cascade = CascadeType.PERSIST)
    private List<Imagem> imagens;

    public Long getId_estrategia() {
        return idEstrategia;
    }

    public void setId_estrategia(Long id_estrategia) {
        this.idEstrategia = id_estrategia;
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

    public String getExemplo() {
        return exemplo;
    }

    public void setExemplo(String exemplo) {
        this.exemplo = exemplo;
    }

    public String getDica() {
        return dica;
    }

    public void setDica(String dica) {
        this.dica = dica;
    }

    public List<SessaoTeste> getSessoes() {
        return sessoes;
    }

    public void setSessoes(List<SessaoTeste> sessoes) {
        this.sessoes = sessoes;
    }

    public List<Imagem> getImagens() {
        return imagens;
    }

    public void setImagens(List<Imagem> imagens) {
        this.imagens = imagens;
    }
}
