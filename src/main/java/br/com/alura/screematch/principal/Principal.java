package br.com.alura.screematch.principal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import br.com.alura.screematch.enums.Categoria;
import br.com.alura.screematch.model.Dados;
import br.com.alura.screematch.model.DadosTemporada;
import br.com.alura.screematch.model.Episodio;
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
	private List<Dados> dadosSeries = new ArrayList<>();
	private SerieRepository repositorio;
	private List<Serie> series = new ArrayList<>();

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
					4 - Buscar série por título
					5 - Buscar série por ator
					6 - Buscar top 5 séries
					7 - Buscar séries por categoria
					8 - Filtrar séries
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
			case 4:
				buscarSeriePorTitulo();
				break;
			case 5:
				buscarSeriesPorAtor();
				break;
			case 6:
				buscarTop5Series();
				break;
			case 7:
				buscarSeriePorCategoria();
				break;
			case 8:
				filtrarSeriesPorTemporadaEAvaliacao();
				break;
			case 0:
				System.out.println("Saindo...");
				break;
			default:
				System.out.println("Opção inválida");
			}
		}
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
		listarSeriesBuscadas();
		System.out.println("Escolha uma série pelo nome: ");
		var nomeSerie = sc.nextLine();

		Optional<Serie> serie = repositorio.findByTituloContainingIgnoreCase(nomeSerie);

		if (serie.isPresent()) {
			var serieEncontrada = serie.get();
			List<DadosTemporada> temporadas = new ArrayList<>();

			for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
				var json = consumoApi.obterDados(
						ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
				DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
				temporadas.add(dadosTemporada);
			}
			temporadas.forEach(System.out::println);

			List<Episodio> episodios = temporadas.stream()
					.flatMap(d -> d.episodios().stream().map(e -> new Episodio(d.numero(), e)))
					.collect(Collectors.toList());

			serieEncontrada.setEpisodios(episodios);
			repositorio.save(serieEncontrada);

		} else {
			System.out.println("Série não encontrada!");
		}

	}

	private void listarSeriesBuscadas() {
		series = repositorio.findAll();
		series.stream().sorted(Comparator.comparing(Serie::getGenero)).forEach(System.out::println);
	}

	private void buscarSeriePorTitulo() {
		System.out.println("Escolha uma série pelo nome: ");
		var nomeSerie = sc.nextLine();
		Optional<Serie> serieBuscada = repositorio.findByTituloContainingIgnoreCase(nomeSerie);

		if (serieBuscada.isPresent()) {
			System.out.println("Série buscada: " + serieBuscada.get());
		} else {
			System.err.println("Série não encontrada!");
		}
	}

	private void buscarSeriesPorAtor() {
		System.out.println("Digite o nomde do autor: ");
		var nomeAutor = sc.nextLine();
		List<Serie> seriesEncontradas = repositorio.findByAtoresContainingIgnoreCase(nomeAutor);
		seriesEncontradas.forEach(s -> System.out.println(s.getTitulo() + ": " + s.getAvaliacao()));
	}

	private void buscarTop5Series() {
		List<Serie> topSeries = repositorio.findTop2ByOrderByAvaliacaoDesc();
		topSeries.forEach(s -> System.out.println(s.getTitulo() + ": " + s.getAvaliacao()));

	}

	private void buscarSeriePorCategoria() {
		System.out.println("Digite a categoria desejada: ");
		var nomeGenero = sc.nextLine();
		Categoria categoria = Categoria.fromPortugues(nomeGenero);
		List<Serie> seriesPorCategoria = repositorio.findByGenero(categoria);
		System.out.println("Séries da categoria: " + nomeGenero);
		seriesPorCategoria.forEach(System.out::println);
	}

	private void filtrarSeriesPorTemporadaEAvaliacao() {
		System.out.println("Filtrar séries até quantas temporadas: ");
		var totalTemporadas = sc.nextInt();
		System.out.println("Com avaliação a partir de que valor? ");
		var avaliacao = sc.nextDouble();
		
		List<Serie> filtroSeries = repositorio.findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(totalTemporadas, avaliacao);
		
		filtroSeries.forEach(s -> System.out.println(s.getTitulo() +" | Avaliação: "+ s.getAvaliacao()));
	}

}
