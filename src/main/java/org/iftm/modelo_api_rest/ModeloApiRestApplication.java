package org.iftm.modelo_api_rest;

import java.time.Instant;
import java.util.List;

import org.iftm.modelo_api_rest.entities.Bloqueio;
import org.iftm.modelo_api_rest.entities.RegraEmprestimo;
import org.iftm.modelo_api_rest.repositories.BloqueioRepository;
import org.iftm.modelo_api_rest.repositories.RegraEmprestimoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ModeloApiRestApplication implements CommandLineRunner {

	@Autowired
	BloqueioRepository bloqueioRepository;

	@Autowired
	RegraEmprestimoRepository regraEmprestimoRepository;

	public static void main(String[] args) {
		SpringApplication.run(ModeloApiRestApplication.class, args);
	}

@Override

public void run(String... args) throws Exception {
		Bloqueio bloquei1 = new Bloqueio(null, "Multa", "25/03/2026", "27/03/2026");
		bloqueioRepository.save(bloquei1);
		RegraEmprestimo regraEmprestimo1 = new RegraEmprestimo(null, "7", "1", "10", "3", "true");
		regraEmprestimo1.setBloqueio(bloquei1);
		regraEmprestimo.save(regraEmprestimo1);

		System.out.println();
		System.out.println();
		System.out.println();
		List<Bloqueio> list = bloqueioRepository.findAll();
		for (Bloqueio blo : list) {
			System.out.println(blo.getMotivo() + " maxima " + blo.getCodigoRegraEmprestimo().getMultaMax());
		}
		System.out.println();
		System.out.println();
		System.out.println();

}
