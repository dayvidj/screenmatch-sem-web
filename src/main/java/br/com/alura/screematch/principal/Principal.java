package br.com.alura.screematch.principal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import br.com.alura.screematch.model.Dados;
import br.com.alura.screematch.model.DadosEpisodio;
import br.com.alura.screematch.model.DadosTemporada;
import br.com.alura.screematch.model.Episodio;
import br.com.alura.screematch.service.ConsumoApi;
import br.com.alura.screematch.service.ConverteDados;

public class Principal {
	private Scanner sc = new Scanner(System.in);
	private ConsumoApi consumoApi = new ConsumoApi();
	private ConverteDados conversor = new ConverteDados();

	private final String ENDERECO = "https://www.omdbapi.com/?t=";
	private final String API_KEY = "&apikey=4944d971";

	public void exibeMenu() {
		System.out.print("Digite o nome da série: ");
		var nomeSerie = sc.nextLine();
		var json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
		System.out.println(json);

		Dados dados = conversor.obterDados(json, Dados.class);
		System.out.println(dados);

		List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i <= dados.totalTemporadas(); i++) {
			json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}

		temporadas.forEach(System.out::println);

		temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

		List<DadosEpisodio> dadosEpisodio = temporadas.stream().flatMap(t -> t.episodios().stream())
				.collect(Collectors.toList());

		System.out.println("\n Top 5 episódios");
		dadosEpisodio.stream().filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
				.sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed()).limit(5)
				.forEach(System.out::println);

		List<Episodio> episodios = temporadas.stream()
				.flatMap(t -> t.episodios().stream()
						.map(d -> new Episodio(t.numero(), d)))
				.collect(Collectors.toList());
		
		episodios.forEach(System.out::println);
	}
}
