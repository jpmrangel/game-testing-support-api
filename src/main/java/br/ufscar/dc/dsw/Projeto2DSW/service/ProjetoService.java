package br.ufscar.dc.dsw.Projeto2DSW.service;

import br.ufscar.dc.dsw.Projeto2DSW.model.Projeto;
import br.ufscar.dc.dsw.Projeto2DSW.model.Usuario;
import br.ufscar.dc.dsw.Projeto2DSW.repository.ProjetoRepository;
import br.ufscar.dc.dsw.Projeto2DSW.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<Projeto> listarTodos(Sort sort) {
        return projetoRepository.findAll(sort);
    }
    
    @Transactional(readOnly = true)
    public List<Projeto> listarTodos() {
        return projetoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Projeto> buscarPorId(Long id) {
        return projetoRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Projeto> listarPorUsuario(Long usuarioId, Sort sort) {
        return projetoRepository.findByUsuarios_IdUsuario(usuarioId, sort);
    }

    public Projeto salvar(Projeto projeto) {
        if (projeto.getUsuarios() != null && !projeto.getUsuarios().isEmpty()) {
            List<Long> ids = projeto.getUsuarios().stream()
                                    .map(Usuario::getId_usuario)
                                    .collect(Collectors.toList());
            List<Usuario> usuariosCompletos = usuarioRepository.findAllById(ids);
            projeto.setUsuarios(usuariosCompletos);
        }
        return projetoRepository.save(projeto);
    }

    public void excluir(Long id) {
        if (!projetoRepository.existsById(id)) {
            throw new RuntimeException("Projeto não encontrado com o id: " + id);
        }
        projetoRepository.deleteById(id);
    }
}