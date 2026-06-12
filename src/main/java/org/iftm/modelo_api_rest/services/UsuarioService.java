package org.iftm.modelo_api_rest.services;

import java.util.List;
import java.util.Optional;

import org.iftm.modelo_api_rest.entities.Usuario;
import org.iftm.modelo_api_rest.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario salvar(Usuario usuario) {
        validarUsuario(usuario);
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> salvarTodos(List<Usuario> usuarios) {
        usuarios.forEach(this::validarUsuario);
        return usuarioRepository.saveAll(usuarios);
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario atualizar(Long id, Usuario usuario) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuario nao encontrado: " + id);
        }
        validarUsuario(usuario);
        usuario.setCodigoUsuario(id);
        return usuarioRepository.save(usuario);
    }

    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }

    public void deletarTodos() {
        usuarioRepository.deleteAll();
    }

    public List<Usuario> buscarPorNome(String nome) {
        return usuarioRepository.findByNome(nome);
    }

    public List<Usuario> buscarPorTipoUsuario(String tipoUsuario) {
        return usuarioRepository.findByTipoUsuario(tipoUsuario);
    }

    public Optional<Usuario> buscarPorCpf(String cpf) {
        return usuarioRepository.findByCpf(cpf);
    }

    public List<Usuario> buscarAtivosPorTipo(String tipo) {
        return usuarioRepository.findAtivosPorTipo(tipo);
    }

    public void validarUsuario(Usuario usuario) {
        validarCpf(usuario.getCpf());
        validarEmail(usuario.getEmail());
        validarTipoUsuario(usuario.getTipoUsuario());
    }

    public void validarCpf(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF deve conter exatamente 11 digitos numericos");
        }
    }

    public void validarEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email invalido");
        }
    }

    public void validarTipoUsuario(String tipoUsuario) {
        if (tipoUsuario == null || (!tipoUsuario.equals("ALUNO")
                && !tipoUsuario.equals("PROFESSOR")
                && !tipoUsuario.equals("FUNCIONARIO"))) {
            throw new IllegalArgumentException("Tipo de usuario deve ser ALUNO, PROFESSOR ou FUNCIONARIO");
        }
    }
}
