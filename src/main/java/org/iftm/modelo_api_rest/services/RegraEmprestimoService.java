package org.iftm.modelo_api_rest.services;

import java.util.List;
import java.util.Optional;

import org.iftm.modelo_api_rest.entities.RegraEmprestimo;
import org.iftm.modelo_api_rest.repositories.RegraEmprestimoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegraEmprestimoService {

    @Autowired
    private RegraEmprestimoRepository regraEmprestimoRepository;

    public RegraEmprestimo salvar(RegraEmprestimo regra) {
        validarRegra(regra);
        return regraEmprestimoRepository.save(regra);
    }

    public List<RegraEmprestimo> salvarTodos(List<RegraEmprestimo> regras) {
        regras.forEach(this::validarRegra);
        return regraEmprestimoRepository.saveAll(regras);
    }

    public Optional<RegraEmprestimo> buscarPorId(Long id) {
        return regraEmprestimoRepository.findById(id);
    }

    public List<RegraEmprestimo> buscarTodos() {
        return regraEmprestimoRepository.findAll();
    }

    public RegraEmprestimo atualizar(Long id, RegraEmprestimo regra) {
        if (!regraEmprestimoRepository.existsById(id)) {
            throw new IllegalArgumentException("Regra nao encontrada: " + id);
        }
        validarRegra(regra);
        regra.setCodigoRegraEmprestimo(id);
        return regraEmprestimoRepository.save(regra);
    }

    public void deletar(Long id) {
        regraEmprestimoRepository.deleteById(id);
    }

    public void deletarTodos() {
        regraEmprestimoRepository.deleteAll();
    }

    public List<RegraEmprestimo> buscarAtivas() {
        return regraEmprestimoRepository.findByAtiva(true);
    }

    public List<RegraEmprestimo> buscarPorPrazoMinimo(int dias) {
        return regraEmprestimoRepository.findByPrazoDiasGreaterThanEqual(dias);
    }

    public List<RegraEmprestimo> buscarAtivasOrdenadasPorPrazo() {
        return regraEmprestimoRepository.findAtivasOrdenadasPorPrazo();
    }

    public void validarRegra(RegraEmprestimo regra) {
        validarPrazoDias(regra.getPrazoDias());
        validarMultas(regra.getMultaPorDia(), regra.getMultaMax());
        validarLimiteEmprestimos(regra.getLimiteEmprestimos());
    }

    public void validarPrazoDias(int prazoDias) {
        if (prazoDias <= 0) {
            throw new IllegalArgumentException("Prazo em dias deve ser maior que zero");
        }
    }

    public void validarMultas(double multaPorDia, double multaMax) {
        if (multaMax < multaPorDia) {
            throw new IllegalArgumentException("Multa maxima deve ser maior ou igual a multa por dia");
        }
    }

    public void validarLimiteEmprestimos(int limiteEmprestimos) {
        if (limiteEmprestimos <= 0) {
            throw new IllegalArgumentException("Limite de emprestimos deve ser maior que zero");
        }
    }
}
