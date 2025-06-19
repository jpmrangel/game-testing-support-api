package br.ufscar.dc.dsw.Projeto2DSW.service;

import br.ufscar.dc.dsw.Projeto2DSW.model.Usuario;
import br.ufscar.dc.dsw.Projeto2DSW.model.Papel;
import br.ufscar.dc.dsw.Projeto2DSW.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> listarPorPapel(Papel papel) {
        return repository.findByPapel(papel);
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Usuario salvar(Usuario usuario) {
        if (usuario.getId_usuario() == null) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }
        return repository.save(usuario);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
