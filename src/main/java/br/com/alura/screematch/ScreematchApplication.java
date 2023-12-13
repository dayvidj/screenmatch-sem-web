package br.com.alura.screematch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.alura.screematch.principal.Principal;
import br.com.alura.screematch.repository.SerieRepository;

@SpringBootApplication
public class ScreematchApplication implements CommandLineRunner {
	
	@Autowired
	private SerieRepository repositorio;
	
	public static void main(String[] args) {
		SpringApplication.run(ScreematchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositorio);	
		principal.exibeMenu();	
	}
}
