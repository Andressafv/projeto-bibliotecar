package org.iftm.modelo_api_rest.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.iftm.modelo_api_rest.entities.Bloqueio;
import org.iftm.modelo_api_rest.repositories.BloqueioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BloqueioService {

    @Autowired
    private BloqueioRepository bloqueioRepository;

    public Bloqueio salvar(Bloqueio bloqueio) {
        validarBloqueio(bloqueio);
        return bloqueioRepository.save(bloqueio);
    }

    public List<Bloqueio> salvarTodos(List<Bloqueio> bloqueios) {
        bloqueios.forEach(this::validarBloqueio);
        return bloqueioRepository.saveAll(bloqueios);
    }

    public Optional<Bloqueio> buscarPorId(Long id) {
        return bloqueioRepository.findById(id);
    }

    public List<Bloqueio> buscarTodos() {
        return bloqueioRepository.findAll();
    }

    public Bloqueio atualizar(Long id, Bloqueio bloqueio) {
        if (!bloqueioRepository.existsById(id)) {
            throw new IllegalArgumentException("Bloqueio nao encontrado: " + id);
        }
        validarBloqueio(bloqueio);
        bloqueio.setCodigoBloqueio(id);
        return bloqueioRepository.save(bloqueio);
    }

    public void deletar(Long id) {
        bloqueioRepository.deleteById(id);
    }

    public void deletarTodos() {
        bloqueioRepository.deleteAll();
    }

    public List<Bloqueio> buscarPorMotivo(String motivo) {
        return bloqueioRepository.findByMotivo(motivo);
    }

    public List<Bloqueio> buscarPorMotivoContendo(String motivo) {
        return bloqueioRepository.findByMotivoContainingIgnoreCase(motivo);
    }

    public List<Bloqueio> buscarBloqueiosAtivos() {
        return bloqueioRepository.findBloqueiosAtivos();
    }

    public void validarBloqueio(Bloqueio bloqueio) {
        validarMotivo(bloqueio.getMotivo());
        validarDatas(bloqueio.getDataInicio(), bloqueio.getDataFim());
    }

    public void validarMotivo(String motivo) {
        if (motivo == null || motivo.isBlank()) {
            throw new IllegalArgumentException("Motivo do bloqueio nao pode ser vazio");
        }
    }

    public void validarDatas(Date dataInicio, Date dataFim) {
        if (dataInicio == null || dataFim == null) {
            throw new IllegalArgumentException("Datas de inicio e fim sao obrigatorias");
        }
        if (!dataFim.after(dataInicio)) {
            throw new IllegalArgumentException("Data fim deve ser posterior a data inicio");
        }
    }

    public boolean verificarBloqueioAtivo(Bloqueio bloqueio) {
        Date hoje = new Date();
        return bloqueio.getDataInicio() != null
                && bloqueio.getDataFim() != null
                && !hoje.before(bloqueio.getDataInicio())
                && !hoje.after(bloqueio.getDataFim());
    }
}
