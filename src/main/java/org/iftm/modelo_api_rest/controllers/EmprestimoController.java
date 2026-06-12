package org.iftm.modelo_api_rest.controllers;

import java.util.Date;
import java.util.List;

import org.iftm.modelo_api_rest.entities.Emprestimo;
import org.iftm.modelo_api_rest.services.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
@RequestMapping("/api/emprestimos")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;

    @GetMapping
    public List<Emprestimo> buscarTodos() {
        return emprestimoService.buscarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Emprestimo> buscarPorId(@PathVariable Long id) {
        return emprestimoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Emprestimo> salvar(@RequestBody Emprestimo emprestimo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(emprestimoService.salvar(emprestimo));
    }

    @PostMapping("/lote")
    public ResponseEntity<List<Emprestimo>> salvarTodos(@RequestBody List<Emprestimo> emprestimos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(emprestimoService.salvarTodos(emprestimos));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Emprestimo> atualizar(@PathVariable Long id, @RequestBody Emprestimo emprestimo) {
        return ResponseEntity.ok(emprestimoService.atualizar(id, emprestimo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        emprestimoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarTodos() {
        emprestimoService.deletarTodos();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/busca/atrasados")
    public List<Emprestimo> buscarEmprestimosAtrasados() {
        return emprestimoService.buscarEmprestimosAtrasados();
    }

    @GetMapping("/busca/periodo")
    public List<Emprestimo> buscarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fim) {
        return emprestimoService.buscarPorPeriodo(inicio, fim);
    }

    @GetMapping("/{id}/dias")
    public ResponseEntity<Long> calcularDiasEmprestimo(@PathVariable Long id) {
        return emprestimoService.buscarPorId(id)
                .map(emprestimo -> ResponseEntity.ok(emprestimoService.calcularDiasEmprestimo(emprestimo)))
                .orElse(ResponseEntity.notFound().build());
    }
}
