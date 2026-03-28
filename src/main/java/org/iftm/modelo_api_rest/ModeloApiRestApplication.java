package org.iftm.modelo_api_rest;

import java.time.Instant;
import java.util.Date;
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
	  
		Bloqueio bloqueio1 = new Bloqueio(null, "Multa", Date.from(Instant.now()), Date.from(Instant.now()));
        bloqueioRepository.save(bloqueio1);
        
	//	RegraEmprestimo regraEmprestimo1 = new RegraEmprestimo(null, 7, 2.0, 20.0, 5, true);
	
	// regraEmprestimoRepository.save(regraEmprestimo1);
	}
}
