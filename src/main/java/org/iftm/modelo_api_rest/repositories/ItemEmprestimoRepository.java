package org.iftm.modelo_api_rest.repositories;

import java.util.Date;
import java.util.List;

import org.iftm.modelo_api_rest.entities.ItemEmprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemEmprestimoRepository extends JpaRepository<ItemEmprestimo, Long> {

    List<ItemEmprestimo> findByStatus(String status);

    List<ItemEmprestimo> findByMultaGeradaGreaterThan(Double multa);

    List<ItemEmprestimo> findByMultaGeradaIsNotNull();

    List<ItemEmprestimo> findByStatusAndMultaGeradaGreaterThan(String status, Double multa);

    List<ItemEmprestimo> findByDataDevolucaoRealAfter(Date data);

    @Query("SELECT SUM(i.multaGerada) FROM ItemEmprestimo i WHERE i.status = :status")
    Double sumMultasByStatus(@Param("status") String status);
}
