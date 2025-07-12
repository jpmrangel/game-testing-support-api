package br.ufscar.dc.dsw.Projeto2DSW.dto;

import br.ufscar.dc.dsw.Projeto2DSW.model.Papel;
import br.ufscar.dc.dsw.Projeto2DSW.model.Usuario;

import java.sql.Timestamp;

public class UsuarioResponseDTO {

    private Long id_usuario;
    private String nome;
    private String email;
    private Papel papel;
    private Timestamp data_criacao;

    public UsuarioResponseDTO(Usuario usuario) {
        this.id_usuario = usuario.getId_usuario();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.papel = usuario.getPapel();
        this.data_criacao = usuario.getData_criacao();
    }

    public Long getId_usuario() { return id_usuario; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public Papel getPapel() { return papel; }
    public Timestamp getData_criacao() { return data_criacao; }
}