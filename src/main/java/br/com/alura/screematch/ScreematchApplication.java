package br.com.alura.screematch;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.alura.screematch.model.Dados;
import br.com.alura.screematch.model.DadosEpisodio;
import br.com.alura.screematch.model.DadosTemporada;
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
		var json = consumoApi.obterDados("http://www.omdbapi.com/?t=the+big+bang+theory&apikey=4944d971");
		System.out.println(json);

		ConverteDados conversor = new ConverteDados();
		Dados dados = conversor.obterDados(json, Dados.class);
		System.out.println(dados);
		
		json = consumoApi.obterDados("http://www.omdbapi.com/?t=the+big+bang+theory&season=1&episode=1&apikey=4944d971");
		DadosEpisodio dadosEpisodio = conversor.obterDados(json, DadosEpisodio.class);
		System.out.println(dadosEpisodio);
		
		List<DadosTemporada> temporadas = new ArrayList<>();
		
		for(int i = 1; i <= dados.totalTemporadas(); i++) {
			json = consumoApi.obterDados("http://www.omdbapi.com/?t=the+big+bang+theory&season="+ i +"&apikey=4944d971");
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		
		temporadas.forEach(System.out::println);
		
	}

}
