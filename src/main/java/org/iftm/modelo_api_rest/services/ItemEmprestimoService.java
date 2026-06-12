package org.iftm.modelo_api_rest.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.iftm.modelo_api_rest.entities.ItemEmprestimo;
import org.iftm.modelo_api_rest.repositories.ItemEmprestimoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemEmprestimoService {

    @Autowired
    private ItemEmprestimoRepository itemEmprestimoRepository;

    public ItemEmprestimo salvar(ItemEmprestimo item) {
        validarItem(item);
        return itemEmprestimoRepository.save(item);
    }

    public List<ItemEmprestimo> salvarTodos(List<ItemEmprestimo> itens) {
        itens.forEach(this::validarItem);
        return itemEmprestimoRepository.saveAll(itens);
    }

    public Optional<ItemEmprestimo> buscarPorId(Long id) {
        return itemEmprestimoRepository.findById(id);
    }

    public List<ItemEmprestimo> buscarTodos() {
        return itemEmprestimoRepository.findAll();
    }

    public ItemEmprestimo atualizar(Long id, ItemEmprestimo item) {
        if (!itemEmprestimoRepository.existsById(id)) {
            throw new IllegalArgumentException("Item nao encontrado: " + id);
        }
        validarItem(item);
        item.setCodigoItemEmprestimo(id);
        return itemEmprestimoRepository.save(item);
    }

    public void deletar(Long id) {
        itemEmprestimoRepository.deleteById(id);
    }

    public void deletarTodos() {
        itemEmprestimoRepository.deleteAll();
    }

    public List<ItemEmprestimo> buscarPorStatus(String status) {
        return itemEmprestimoRepository.findByStatus(status);
    }

    public List<ItemEmprestimo> buscarComMultaMaiorQue(Double multa) {
        return itemEmprestimoRepository.findByMultaGeradaGreaterThan(multa);
    }

    public Double somarMultasPorStatus(String status) {
        Double total = itemEmprestimoRepository.sumMultasByStatus(status);
        return total != null ? total : 0.0;
    }

    public void validarItem(ItemEmprestimo item) {
        validarStatus(item.getStatus());
    }

    public void validarStatus(String status) {
        if (status == null || (!status.equals("PENDENTE")
                && !status.equals("DEVOLVIDO")
                && !status.equals("ATRASADO"))) {
            throw new IllegalArgumentException("Status deve ser PENDENTE, DEVOLVIDO ou ATRASADO");
        }
    }

    public boolean verificarAtraso(ItemEmprestimo item) {
        if (item.getDataDevolucaoReal() == null || item.getDataDevolucaoPrevista() == null) {
            return false;
        }
        return item.getDataDevolucaoReal().after(item.getDataDevolucaoPrevista());
    }

    public double calcularMulta(ItemEmprestimo item, double multaPorDia) {
        if (item.getDataDevolucaoReal() == null || item.getDataDevolucaoPrevista() == null) {
            return 0.0;
        }
        if (!verificarAtraso(item)) {
            return 0.0;
        }
        long diasAtraso = TimeUnit.MILLISECONDS.toDays(
                item.getDataDevolucaoReal().getTime() - item.getDataDevolucaoPrevista().getTime());
        return diasAtraso * multaPorDia;
    }
}
