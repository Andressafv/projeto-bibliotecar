package org.iftm.modelo_api_rest.controllers;

import java.util.List;

import org.iftm.modelo_api_rest.entities.ItemEmprestimo;
import org.iftm.modelo_api_rest.services.ItemEmprestimoService;
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
@RequestMapping("/api/itens")
public class ItemEmprestimoController {

    @Autowired
    private ItemEmprestimoService itemEmprestimoService;

    @GetMapping
    public List<ItemEmprestimo> buscarTodos() {
        return itemEmprestimoService.buscarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemEmprestimo> buscarPorId(@PathVariable Long id) {
        return itemEmprestimoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ItemEmprestimo> salvar(@RequestBody ItemEmprestimo item) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemEmprestimoService.salvar(item));
    }

    @PostMapping("/lote")
    public ResponseEntity<List<ItemEmprestimo>> salvarTodos(@RequestBody List<ItemEmprestimo> itens) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemEmprestimoService.salvarTodos(itens));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemEmprestimo> atualizar(@PathVariable Long id, @RequestBody ItemEmprestimo item) {
        return ResponseEntity.ok(itemEmprestimoService.atualizar(id, item));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        itemEmprestimoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarTodos() {
        itemEmprestimoService.deletarTodos();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/busca/status")
    public List<ItemEmprestimo> buscarPorStatus(@RequestParam String status) {
        return itemEmprestimoService.buscarPorStatus(status);
    }

    @GetMapping("/busca/multa")
    public List<ItemEmprestimo> buscarComMultaMaiorQue(@RequestParam Double multa) {
        return itemEmprestimoService.buscarComMultaMaiorQue(multa);
    }

    @GetMapping("/busca/soma-multas")
    public Double somarMultasPorStatus(@RequestParam String status) {
        return itemEmprestimoService.somarMultasPorStatus(status);
    }

    @GetMapping("/{id}/atraso")
    public ResponseEntity<Boolean> verificarAtraso(@PathVariable Long id) {
        return itemEmprestimoService.buscarPorId(id)
                .map(item -> ResponseEntity.ok(itemEmprestimoService.verificarAtraso(item)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/calcular-multa")
    public ResponseEntity<Double> calcularMulta(@PathVariable Long id, @RequestParam double multaPorDia) {
        return itemEmprestimoService.buscarPorId(id)
                .map(item -> ResponseEntity.ok(itemEmprestimoService.calcularMulta(item, multaPorDia)))
                .orElse(ResponseEntity.notFound().build());
    }
}
