package org.iftm.modelo_api_rest.repositories;

import java.util.List;

import org.iftm.modelo_api_rest.entities.RegraEmprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RegraEmprestimoRepository extends JpaRepository<RegraEmprestimo, Long> {

    List<RegraEmprestimo> findByAtiva(boolean ativa);

    List<RegraEmprestimo> findByPrazoDiasGreaterThanEqual(int dias);

    List<RegraEmprestimo> findByLimiteEmprestimosGreaterThanEqual(int limite);

    List<RegraEmprestimo> findByMultaPorDiaLessThanEqual(double multa);

    List<RegraEmprestimo> findByAtivaAndLimiteEmprestimosGreaterThan(boolean ativa, int limite);

    @Query("SELECT r FROM RegraEmprestimo r WHERE r.ativa = true ORDER BY r.prazoDias DESC")
    List<RegraEmprestimo> findAtivasOrdenadasPorPrazo();
}
