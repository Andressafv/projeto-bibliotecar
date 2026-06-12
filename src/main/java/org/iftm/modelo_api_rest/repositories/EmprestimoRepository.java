package org.iftm.modelo_api_rest.repositories;

import java.util.Date;
import java.util.List;

import org.iftm.modelo_api_rest.entities.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    List<Emprestimo> findByDataEmprestimoAfter(Date data);

    List<Emprestimo> findByDataEmprestimoBefore(Date data);

    List<Emprestimo> findByDataDevolucaoPrevistaBefore(Date data);

    List<Emprestimo> findByDataEmprestimoBetween(Date inicio, Date fim);

    List<Emprestimo> findByDataDevolucaoPrevistaAfter(Date data);

    @Query("SELECT e FROM Emprestimo e WHERE e.dataDevolucaoPrevista < CURRENT_DATE")
    List<Emprestimo> findEmprestimosAtrasados();
}
