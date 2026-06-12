package org.iftm.modelo_api_rest.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.iftm.modelo_api_rest.entities.Emprestimo;
import org.iftm.modelo_api_rest.repositories.EmprestimoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    public Emprestimo salvar(Emprestimo emprestimo) {
        validarEmprestimo(emprestimo);
        return emprestimoRepository.save(emprestimo);
    }

    public List<Emprestimo> salvarTodos(List<Emprestimo> emprestimos) {
        emprestimos.forEach(this::validarEmprestimo);
        return emprestimoRepository.saveAll(emprestimos);
    }

    public Optional<Emprestimo> buscarPorId(Long id) {
        return emprestimoRepository.findById(id);
    }

    public List<Emprestimo> buscarTodos() {
        return emprestimoRepository.findAll();
    }

    public Emprestimo atualizar(Long id, Emprestimo emprestimo) {
        if (!emprestimoRepository.existsById(id)) {
            throw new IllegalArgumentException("Emprestimo nao encontrado: " + id);
        }
        validarEmprestimo(emprestimo);
        emprestimo.setCodigoEmprestimo(id);
        return emprestimoRepository.save(emprestimo);
    }

    public void deletar(Long id) {
        emprestimoRepository.deleteById(id);
    }

    public void deletarTodos() {
        emprestimoRepository.deleteAll();
    }

    public List<Emprestimo> buscarEmprestimosAtrasados() {
        return emprestimoRepository.findEmprestimosAtrasados();
    }

    public List<Emprestimo> buscarPorPeriodo(Date inicio, Date fim) {
        return emprestimoRepository.findByDataEmprestimoBetween(inicio, fim);
    }

    public List<Emprestimo> buscarComDevolucaoApos(Date data) {
        return emprestimoRepository.findByDataDevolucaoPrevistaAfter(data);
    }

    public void validarEmprestimo(Emprestimo emprestimo) {
        validarDataEmprestimo(emprestimo.getDataEmprestimo());
        validarDatasEmprestimo(emprestimo.getDataEmprestimo(), emprestimo.getDataDevolucaoPrevista());
    }

    public void validarDataEmprestimo(Date dataEmprestimo) {
        if (dataEmprestimo == null) {
            throw new IllegalArgumentException("Data do emprestimo nao pode ser nula");
        }
    }

    public void validarDatasEmprestimo(Date dataEmprestimo, Date dataDevolucaoPrevista) {
        if (dataDevolucaoPrevista == null) {
            throw new IllegalArgumentException("Data de devolucao prevista nao pode ser nula");
        }
        if (!dataDevolucaoPrevista.after(dataEmprestimo)) {
            throw new IllegalArgumentException("Data de devolucao prevista deve ser posterior a data do emprestimo");
        }
    }

    public long calcularDiasEmprestimo(Emprestimo emprestimo) {
        validarEmprestimo(emprestimo);
        long diff = emprestimo.getDataDevolucaoPrevista().getTime() - emprestimo.getDataEmprestimo().getTime();
        return TimeUnit.MILLISECONDS.toDays(diff);
    }
}
