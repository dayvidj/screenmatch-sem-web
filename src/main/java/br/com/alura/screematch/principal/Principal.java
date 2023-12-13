package br.com.alura.screematch.principal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import br.com.alura.screematch.model.Dados;
import br.com.alura.screematch.model.DadosTemporada;
import br.com.alura.screematch.model.Serie;
import br.com.alura.screematch.repository.SerieRepository;
import br.com.alura.screematch.service.ConsumoApi;
import br.com.alura.screematch.service.ConverteDados;

public class Principal {
	private Scanner sc = new Scanner(System.in);
	private ConsumoApi consumoApi = new ConsumoApi();
	private ConverteDados conversor = new ConverteDados();

	private final String ENDERECO = "https://www.omdbapi.com/?t=";
	private final String API_KEY = "&apikey=4944d971";
	private List<Dados> dadosSeries =  new ArrayList<>();
	private SerieRepository repositorio;
	
	public Principal(SerieRepository repositorio) {
		this.repositorio = repositorio;
	}

	public void exibeMenu() {

		var opcao = -1;
		while (opcao != 0) {
			var menu = """
					1 - Buscar séries
					2 - Buscar episódios
					3 - Listar séries buscadas
					0 - Sair
					""";

			System.out.println(menu);
			opcao = sc.nextInt();
			sc.nextLine();

			switch (opcao) {
			case 1:
				buscarSerieWeb();
				break;
			case 2:
				buscarEpisodioPorSerie();
				break;
			case 3: 
				listarSeriesBuscadas();
				break;
			case 0:
				System.out.println("Saindo...");
				break;
			default:
				System.out.println("Opção inválida");
			}
		}
	}

	private void listarSeriesBuscadas() {
		List<Serie> series = repositorio.findAll();
		series.stream().sorted(Comparator.comparing(Serie::getGenero)).forEach(System.out::println);
	}

	private void buscarSerieWeb() {
		Dados dados = getDados();
		Serie serie = new Serie(dados);
//		dadosSeries.add(dados);
		repositorio.save(serie);
		System.out.println(dados);
	}

	private Dados getDados() {
		System.out.println("Digite o nome da série para busca");
		var nomeSerie = sc.nextLine();
		var json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
		Dados dados = conversor.obterDados(json, Dados.class);
		return dados;
	}

	private void buscarEpisodioPorSerie() {
		Dados dadosSerie = getDados();
		List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
			var json = consumoApi
					.obterDados(ENDERECO + dadosSerie.titulo().replace(" ", "+") + "&season=" + i + API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);

	}
}
