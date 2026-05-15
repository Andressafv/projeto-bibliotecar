package org.iftm.modelo_api_rest.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.iftm.modelo_api_rest.entities.Bloqueio;
import org.iftm.modelo_api_rest.repositories.BloqueioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BloqueioService {
    
  @Autowired
    private BloqueioRepository repository;

    public List<Bloqueio> findAll() {
        return repository.findAll();
    }
  
    public Optional<Bloqueio> findById(Long id) {
        return repository.findById(id);
    }

    public Bloqueio save(Bloqueio bloqueio) {
        
        if (bloqueio.getDataFim() != null && bloqueio.getDataInicio().after(bloqueio.getDataFim())) {
        throw new IllegalArgumentException("A data de início do bloqueio não pode ser posterior à data final.");
        }
        return repository.save(bloqueio);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Bloqueio update(Long id, Bloqueio bloqueio) {
        Optional<Bloqueio> existingBloqueio = repository.findById(id);
        if (existingBloqueio.isPresent()) {
            Bloqueio updatedBloqueio = existingBloqueio.get();
            updatedBloqueio.setMotivo(bloqueio.getMotivo());
            updatedBloqueio.setDataInicio(bloqueio.getDataInicio());
            updatedBloqueio.setDataFim(bloqueio.getDataFim());
            return repository.save(updatedBloqueio);
        } else {
            throw new RuntimeException("Bloqueio não encontrado com id: " + id);
        }
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public long count() {
        return repository.count();
    }   

    public void deleteAll() {
        repository.deleteAll();
    }
    


    

}
