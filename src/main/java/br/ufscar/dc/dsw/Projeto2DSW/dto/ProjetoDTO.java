package br.ufscar.dc.dsw.Projeto2DSW.dto;

import br.ufscar.dc.dsw.Projeto2DSW.model.Projeto;
import br.ufscar.dc.dsw.Projeto2DSW.model.Usuario;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

public class ProjetoDTO {
    private Long id_projeto;
    private String nome;
    private String descricao;
    private Timestamp dataCriacao;
    private List<String> membros;

    public ProjetoDTO(Projeto projeto) {
        this.id_projeto = projeto.getId_projeto();
        this.nome = projeto.getNome();
        this.descricao = projeto.getDescricao();
        this.dataCriacao = projeto.getDataCriacao();
        if (projeto.getUsuarios() != null) {
            this.membros = projeto.getUsuarios()
                    .stream()
                    .map(Usuario::getNome)
                    .collect(Collectors.toList());
        }
    }

    // Getters
    public Long getId_projeto() { return id_projeto; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public Timestamp getDataCriacao() { return dataCriacao; }
    public List<String> getMembros() { return membros; }
}