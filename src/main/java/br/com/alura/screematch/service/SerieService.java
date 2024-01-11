package br.com.alura.screematch.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.screematch.dto.EpisodioDTO;
import br.com.alura.screematch.dto.SerieDTO;
import br.com.alura.screematch.enums.Categoria;
import br.com.alura.screematch.model.Episodio;
import br.com.alura.screematch.model.Serie;
import br.com.alura.screematch.repository.SerieRepository;

@Service
public class SerieService {

	@Autowired
	private SerieRepository repositorio;

	private List<SerieDTO> converteDados(List<Serie> series) {
		return series.stream().map(s -> new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(),
				s.getGenero(), s.getAtores(), s.getPoster(), s.getSinopse())).collect(Collectors.toList());
	}
	
	private List<EpisodioDTO> converteDadosEpisodio(List<Episodio> episodios) {
		return episodios.stream()
				.map(e -> new EpisodioDTO(e.getTemporada(), e.getTitulo(), e.getNumeroEpisodio()))
				.collect(Collectors.toList());
	}

	public List<SerieDTO> obterTodasAsSeries() {
		return converteDados(repositorio.findAll());
	}

	public List<SerieDTO> ObterTop5Series() {
		return converteDados(repositorio.findTop5ByOrderByAvaliacaoDesc());
	}


	public List<SerieDTO> obterLancamentos() {
		return converteDados(repositorio.encontrarEpisodiosMaisRecentes());
	}

	public SerieDTO obterPorId(Long id) {
		Optional<Serie> serie = repositorio.findById(id);

		if (serie.isPresent()) {
			Serie s = serie.get();
			return new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(),
					s.getAtores(), s.getPoster(), s.getSinopse());
		}
		return null;
	}

	public List<EpisodioDTO> obterTodasAsTemporadas(Long id) {
		Optional<Serie> serie = repositorio.findById(id);

		if (serie.isPresent()) {
			Serie s = serie.get();
			return converteDadosEpisodio(s.getEpisodios());
		}
		return null;
	}

	public List<EpisodioDTO> obterTemporadasPorNumero(Long id, Long numero) {
		return converteDadosEpisodio(repositorio.obterEpisodiosPorTemporada(id, numero));
				
	}

	public List<SerieDTO> obterSeriesPorCategoria(String nomeGenero) {
		Categoria categoria = Categoria.fromPortugues(nomeGenero);
		return converteDados(repositorio.findByGenero(categoria));
	}

//	public List<EpisodioDTO> obterTop5Episodios(Long id) {
//		Optional<Serie> serie = repositorio.findById(id);
//
//		if (serie.isPresent()) {
//			Serie s = serie.get();
//			return converteDadosEpisodio(repositorio.topEpisodiosPorSerie(s));
//		}
//		return null;
//	}

}
