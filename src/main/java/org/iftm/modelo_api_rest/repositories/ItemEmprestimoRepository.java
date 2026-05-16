package org.iftm.modelo_api_rest.repositories;

import java.sql.Date;
import java.util.List;
import org.iftm.modelo_api_rest.entities.ItemEmprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemEmprestimoRepository extends JpaRepository<ItemEmprestimo, Long> {
    
    List<ItemEmprestimo> findByStatus(String status);
    
    List<ItemEmprestimo> findByDataDevolucaoPrevista(Date data);
    
    List<ItemEmprestimo> findByMultaGeradaGreaterThan(double valor);
}