package org.iftm.modelo_api_rest.services;

import java.sql.Date;
import java.util.List;
import org.iftm.modelo_api_rest.entities.ItemEmprestimo;
import org.iftm.modelo_api_rest.repositories.ItemEmprestimoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemEmprestimoService {

    @Autowired
    private ItemEmprestimoRepository repository;

    public void validarDataPrevista(Date prevista, Date real) {
        if (prevista == null) {
            throw new IllegalArgumentException("A data de devolução prevista deve ser preenchida.");
        }
        if (real != null && prevista.after(real)) {
            throw new IllegalArgumentException("A data prevista não pode ser posterior à data de devolução real.");
        }
    }

    public void validarStatus(ItemEmprestimo item) {
        if (item.getStatus() == null || item.getStatus().trim().isEmpty()) {
            item.setStatus("EMPRESTADO");
        }
        if (!"EMPRESTADO".equals(item.getStatus()) && !"DEVOLVIDO".equals(item.getStatus())) {
            throw new IllegalArgumentException("Status inválido. Use EMPRESTADO ou DEVOLVIDO.");
        }
    }

    public void calcularEGerarMulta(ItemEmprestimo item) {
        if (item.getDataDevolucaoReal() != null) {
            long milis = item.getDataDevolucaoReal().getTime() - item.getDataDevolucaoPrevista().getTime();
            if (milis > 0) {
                long diasAtraso = milis / (24 * 60 * 60 * 1000);
                item.setMultaGerada(diasAtraso * 2.00); // R$ 2,00 por dia
                item.setStatus("DEVOLVIDO");
            } else {
                item.setMultaGerada(0.0);
                item.setStatus("DEVOLVIDO");
            }
        } else {
            item.setMultaGerada(0.0);
        }
    }

    private void aplicarRegrasDoObjeto(ItemEmprestimo item) {
        validarDataPrevista(item.getDataDevolucaoPrevista(), item.getDataDevolucaoReal());
        calcularEGerarMulta(item);
        validarStatus(item);
    }


    public ItemEmprestimo save(ItemEmprestimo item) {
        aplicarRegrasDoObjeto(item);
        return repository.save(item);
    }

    public List<ItemEmprestimo> saveAll(List<ItemEmprestimo> itens) {
        for (ItemEmprestimo item : itens) {
            aplicarRegrasDoObjeto(item);
        }
        return repository.saveAll(itens);
    }

    public ItemEmprestimo update(Long id, ItemEmprestimo item) {
        ItemEmprestimo existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));
        
        existente.setDataDevolucaoPrevista(item.getDataDevolucaoPrevista());
        existente.setDataDevolucaoReal(item.getDataDevolucaoReal());
        existente.setStatus(item.getStatus());

        aplicarRegrasDoObjeto(existente);
        return repository.save(existente);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public List<ItemEmprestimo> buscarPorStatus(String status) {
        return repository.findByStatus(status);
    }

    public List<ItemEmprestimo> buscarPorVencimento(Date data) {
        return repository.findByDataDevolucaoPrevista(data);
    }

    public List<ItemEmprestimo> buscarItensComMulta() {
        return repository.findByMultaGeradaGreaterThan(0.0);
    }
}