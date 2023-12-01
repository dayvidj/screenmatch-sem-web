package br.com.alura.screematch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.alura.screematch.model.Dados;
import br.com.alura.screematch.service.ConsumoApi;
import br.com.alura.screematch.service.ConverteDados;

@SpringBootApplication
public class ScreematchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreematchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoApi = new ConsumoApi();
		// Obtém os dados do filme na api e atribui para a variável json
		var json = consumoApi.obterDados("http://www.omdbapi.com/?t=matrix&apikey=4944d971");
		System.out.println(json);

		ConverteDados conversor = new ConverteDados();
		Dados dados = conversor.obterDados(json, Dados.class);
		System.out.println(dados);
	}

}
