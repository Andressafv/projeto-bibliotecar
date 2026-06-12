package org.iftm.modelo_api_rest.controllers;

import java.util.List;

import org.iftm.modelo_api_rest.entities.Usuario;
import org.iftm.modelo_api_rest.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> buscarTodos() {
        return usuarioService.buscarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.salvar(usuario));
    }

    @PostMapping("/lote")
    public ResponseEntity<List<Usuario>> salvarTodos(@RequestBody List<Usuario> usuarios) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.salvarTodos(usuarios));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.atualizar(id, usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarTodos() {
        usuarioService.deletarTodos();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/busca/nome")
    public List<Usuario> buscarPorNome(@RequestParam String nome) {
        return usuarioService.buscarPorNome(nome);
    }

    @GetMapping("/busca/tipo")
    public List<Usuario> buscarPorTipo(@RequestParam String tipo) {
        return usuarioService.buscarPorTipoUsuario(tipo);
    }

    @GetMapping("/busca/cpf")
    public ResponseEntity<Usuario> buscarPorCpf(@RequestParam String cpf) {
        return usuarioService.buscarPorCpf(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/busca/ativos")
    public List<Usuario> buscarAtivosPorTipo(@RequestParam String tipo) {
        return usuarioService.buscarAtivosPorTipo(tipo);
    }
}
