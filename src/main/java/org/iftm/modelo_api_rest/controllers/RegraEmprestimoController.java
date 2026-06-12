package org.iftm.modelo_api_rest.controllers;

import java.util.List;

import org.iftm.modelo_api_rest.entities.RegraEmprestimo;
import org.iftm.modelo_api_rest.services.RegraEmprestimoService;
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
@RequestMapping("/api/regras")
public class RegraEmprestimoController {

    @Autowired
    private RegraEmprestimoService regraEmprestimoService;

    @GetMapping
    public List<RegraEmprestimo> buscarTodos() {
        return regraEmprestimoService.buscarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegraEmprestimo> buscarPorId(@PathVariable Long id) {
        return regraEmprestimoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RegraEmprestimo> salvar(@RequestBody RegraEmprestimo regra) {
        return ResponseEntity.status(HttpStatus.CREATED).body(regraEmprestimoService.salvar(regra));
    }

    @PostMapping("/lote")
    public ResponseEntity<List<RegraEmprestimo>> salvarTodos(@RequestBody List<RegraEmprestimo> regras) {
        return ResponseEntity.status(HttpStatus.CREATED).body(regraEmprestimoService.salvarTodos(regras));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegraEmprestimo> atualizar(@PathVariable Long id, @RequestBody RegraEmprestimo regra) {
        return ResponseEntity.ok(regraEmprestimoService.atualizar(id, regra));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        regraEmprestimoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarTodos() {
        regraEmprestimoService.deletarTodos();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/busca/ativas")
    public List<RegraEmprestimo> buscarAtivas() {
        return regraEmprestimoService.buscarAtivas();
    }

    @GetMapping("/busca/prazo")
    public List<RegraEmprestimo> buscarPorPrazoMinimo(@RequestParam int dias) {
        return regraEmprestimoService.buscarPorPrazoMinimo(dias);
    }

    @GetMapping("/busca/ordenadas")
    public List<RegraEmprestimo> buscarAtivasOrdenadasPorPrazo() {
        return regraEmprestimoService.buscarAtivasOrdenadasPorPrazo();
    }
}
