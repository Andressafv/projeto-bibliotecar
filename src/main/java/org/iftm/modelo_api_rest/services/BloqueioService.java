package org.iftm.modelo_api_rest.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import org.iftm.modelo_api_rest.entities.Bloqueio;
import org.iftm.modelo_api_rest.repositories.BloqueioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BloqueioService {
    
    @Autowired
    private BloqueioRepository repository;

    public void validarDatas(Date inicio, Date fim) {
        if (inicio == null) {
            throw new IllegalArgumentException("A data de início deve ser informada.");
        }
        if (fim != null && inicio.after(fim)) {
            throw new IllegalArgumentException("A data de início não pode ser depois da data final.");
        }
    }

    public void validarTamanhoMotivo(String motivo) {
        if (motivo == null || motivo.trim().isEmpty()) {
            throw new IllegalArgumentException("O motivo do bloqueio é obrigatório.");
        }
        if (motivo.length() > 100) {
            throw new IllegalArgumentException("O motivo não pode ter mais de 100 caracteres.");
        }
    }

    public void aplicarBloqueioAutomatico(Bloqueio bloqueio, int diasAtraso, double valorMulta) {
        if (diasAtraso > 10 || valorMulta > 50.00) {
            bloqueio.setMotivo("Bloqueio automático: Atraso superior a 10 dias ou multa superior a R$50,00.");
        }
    }

    private void executarValidacoes(Bloqueio b, String situacaoUsuario, int dias, double multa) {
        if ("BLOQUEADO".equalsIgnoreCase(situacaoUsuario)) {
            throw new IllegalStateException("O usuário já se encontra bloqueado.");
        }
        if (b.getDataInicio() == null) {
            b.setDataInicio(Date.valueOf(LocalDate.now())); 
        }
        validarDatas(b.getDataInicio(), b.getDataFim());
        aplicarBloqueioAutomatico(b, dias, multa);
        validarTamanhoMotivo(b.getMotivo());
    }

    public Bloqueio save(Bloqueio bloqueio, String situacaoUsuario, int diasAtraso, double valorMulta) {
        executarValidacoes(bloqueio, situacaoUsuario, diasAtraso, valorMulta);
        return repository.save(bloqueio);
    }

    public List<Bloqueio> saveAll(List<Bloqueio> bloqueios) {
        for (Bloqueio b : bloqueios) {
            executarValidacoes(b, "ATIVO", 0, 0.0);
        }
        return repository.saveAll(bloqueios);
    }

    public Bloqueio update(Long id, Bloqueio bloqueio, int diasAtraso, double valorMulta) {
        Bloqueio existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bloqueio não encontrado"));
        
        if (bloqueio.getDataFim() != null && (diasAtraso > 0 || valorMulta > 0)) {
            throw new IllegalStateException("O desbloqueio só pode ocorrer após a regularização das pendências.");
        }

        existente.setDataInicio(bloqueio.getDataInicio() != null ? bloqueio.getDataInicio() : existente.getDataInicio());
        existente.setDataFim(bloqueio.getDataFim());
        existente.setMotivo(bloqueio.getMotivo());

        executarValidacoes(existente, "ATIVO", 0, 0.0); 
        return repository.save(existente);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public List<Bloqueio> buscarPorMotivo(String texto) {
        return repository.findByMotivoContaining(texto);
    }

    public List<Bloqueio> buscarBloqueiosAtivos() {
        return repository.findByDataFimIsNull();
    }

    public List<Bloqueio> buscarBloqueiosCriadosHoje() {
        Date hoje = Date.valueOf(LocalDate.now());
        return repository.findByDataInicio(hoje);
    }
}