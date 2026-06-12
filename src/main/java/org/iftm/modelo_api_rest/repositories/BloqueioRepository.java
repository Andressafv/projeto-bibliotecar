package org.iftm.modelo_api_rest.repositories;

import java.util.Date;
import java.util.List;

import org.iftm.modelo_api_rest.entities.Bloqueio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BloqueioRepository extends JpaRepository<Bloqueio, Long> {

    List<Bloqueio> findByMotivo(String motivo);

    List<Bloqueio> findByDataFimAfter(Date data);

    List<Bloqueio> findByDataInicioBefore(Date data);

    List<Bloqueio> findByMotivoContainingIgnoreCase(String motivo);

    List<Bloqueio> findByDataInicioBetween(Date inicio, Date fim);

    @Query("SELECT b FROM Bloqueio b WHERE b.dataFim >= CURRENT_DATE AND b.dataInicio <= CURRENT_DATE")
    List<Bloqueio> findBloqueiosAtivos();
}
