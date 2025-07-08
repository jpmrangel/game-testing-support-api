package br.ufscar.dc.dsw.Projeto2DSW.dto;

import br.ufscar.dc.dsw.Projeto2DSW.model.Estrategia;
import br.ufscar.dc.dsw.Projeto2DSW.model.Imagem;

import java.util.List;
import java.util.stream.Collectors;

public class EstrategiaDTO {

    private Long id;
    private String nome;
    private String descricao;
    private String exemplo;
    private String dica;
    private List<String> imagens;

    public EstrategiaDTO(Estrategia estrategia) {
        this.id = estrategia.getId_estrategia();
        this.nome = estrategia.getNome();
        this.descricao = estrategia.getDescricao();
        this.exemplo = estrategia.getExemplo();
        this.dica = estrategia.getDica();
        if (estrategia.getImagens() != null) {
            this.imagens = estrategia.getImagens()
                    .stream()
                    .map(Imagem::getUrl)
                    .collect(Collectors.toList());
        }
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getExemplo() { return exemplo; }
    public void setExemplo(String exemplo) { this.exemplo = exemplo; }

    public String getDica() { return dica; }
    public void setDica(String dica) { this.dica = dica; }

    public List<String> getImagens() { return imagens; }
    public void setImagens(List<String> imagens) { this.imagens = imagens; }
}
