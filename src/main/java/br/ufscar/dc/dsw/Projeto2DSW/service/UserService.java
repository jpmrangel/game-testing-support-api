package br.ufscar.dc.dsw.Projeto2DSW.service;

import br.ufscar.dc.dsw.Projeto2DSW.model.Papel;
import br.ufscar.dc.dsw.Projeto2DSW.model.Usuario;
import br.ufscar.dc.dsw.Projeto2DSW.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    @Autowired
    private final UsuarioRepository repository;

    public UserService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public void salvar(Usuario usuario) {
        repository.saveAndFlush(usuario);
    }

    public Usuario buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Usuario> buscarTodos() {
        return repository.findAll();
    }

    public List<Usuario> buscarPorPapel(Papel papel) {
        return repository.findByPapel(papel);
    }

    public void remover(Long id) {
        repository.deleteById(id);
    }
}
