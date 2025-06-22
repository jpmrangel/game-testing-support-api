package br.ufscar.dc.dsw.Projeto2DSW.service;

import br.ufscar.dc.dsw.Projeto2DSW.model.Papel;
import br.ufscar.dc.dsw.Projeto2DSW.model.Usuario;
import br.ufscar.dc.dsw.Projeto2DSW.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = false)
public class UserService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario salvar(Usuario usuario) {
        Optional<Usuario> usuarioExistente = repository.findByEmail(usuario.getEmail());
        if (usuarioExistente.isPresent() && !usuarioExistente.get().getId_usuario().equals(usuario.getId_usuario())) {
            throw new DataIntegrityViolationException("error.email.exists");
        }

        if (usuario.getId_usuario() == null || !usuario.getSenha().isEmpty()) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        } else {
            repository.findById(usuario.getId_usuario()).ifPresent(dbUser -> usuario.setSenha(dbUser.getSenha()));
        }

        return repository.save(usuario);
    }

    public void excluir(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("error.user.not.found"));
        if (!usuario.getSessoes().isEmpty() || !usuario.getProjetos().isEmpty()) {
            throw new DataIntegrityViolationException("error.user.in.use");
        }

        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarPorPapel(Papel papel) {
        return repository.findByPapel(papel);
    }

    public Optional<Usuario> findById(Long id) {
        return repository.findById(id);
    }

}