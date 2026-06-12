package org.iftm.modelo_api_rest.controllers;

import java.util.List;

import org.iftm.modelo_api_rest.entities.Bloqueio;
import org.iftm.modelo_api_rest.services.BloqueioService;
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
@RequestMapping("/api/bloqueios")
public class BloqueioController {

    @Autowired
    private BloqueioService bloqueioService;

    @GetMapping
    public List<Bloqueio> buscarTodos() {
        return bloqueioService.buscarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bloqueio> buscarPorId(@PathVariable Long id) {
        return bloqueioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Bloqueio> salvar(@RequestBody Bloqueio bloqueio) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bloqueioService.salvar(bloqueio));
    }

    @PostMapping("/lote")
    public ResponseEntity<List<Bloqueio>> salvarTodos(@RequestBody List<Bloqueio> bloqueios) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bloqueioService.salvarTodos(bloqueios));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bloqueio> atualizar(@PathVariable Long id, @RequestBody Bloqueio bloqueio) {
        return ResponseEntity.ok(bloqueioService.atualizar(id, bloqueio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        bloqueioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarTodos() {
        bloqueioService.deletarTodos();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/busca/motivo")
    public List<Bloqueio> buscarPorMotivo(@RequestParam String motivo) {
        return bloqueioService.buscarPorMotivo(motivo);
    }

    @GetMapping("/busca/motivo-contendo")
    public List<Bloqueio> buscarPorMotivoContendo(@RequestParam String motivo) {
        return bloqueioService.buscarPorMotivoContendo(motivo);
    }

    @GetMapping("/busca/ativos")
    public List<Bloqueio> buscarBloqueiosAtivos() {
        return bloqueioService.buscarBloqueiosAtivos();
    }

    @GetMapping("/{id}/ativo")
    public ResponseEntity<Boolean> verificarBloqueioAtivo(@PathVariable Long id) {
        return bloqueioService.buscarPorId(id)
                .map(bloqueio -> ResponseEntity.ok(bloqueioService.verificarBloqueioAtivo(bloqueio)))
                .orElse(ResponseEntity.notFound().build());
    }
}
