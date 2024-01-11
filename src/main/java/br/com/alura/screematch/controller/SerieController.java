package br.com.alura.screematch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.screematch.dto.EpisodioDTO;
import br.com.alura.screematch.dto.SerieDTO;
import br.com.alura.screematch.service.SerieService;

@RestController
@RequestMapping("/series")
public class SerieController {
	
	@Autowired
	private SerieService servico;
	
	@GetMapping
	public List<SerieDTO> obterSeries() {
		return servico.obterTodasAsSeries();
	}
	
	@GetMapping("/top5")
	public List<SerieDTO> obterTop5Series() {
		return servico.ObterTop5Series();
	}

	@GetMapping("/lancamentos")
	public List<SerieDTO> obterLancamentos() {
		return servico.obterLancamentos();
	}
	
	@GetMapping("/{id}")
	public SerieDTO obterPorId(@PathVariable Long id) {
		return servico.obterPorId(id);
	}
	
	@GetMapping("/{id}/temporadas/todas")
	public List<EpisodioDTO> obterTodasAsTemporadas(@PathVariable Long id) {
		return servico.obterTodasAsTemporadas(id);
	}
	
	@GetMapping("/{id}/temporadas/{numero}")
	public List<EpisodioDTO> obterTodasAsTemporadas(@PathVariable Long id, @PathVariable Long numero) {
		return servico.obterTemporadasPorNumero(id, numero);
	}

	@GetMapping("/categoria/{nomeGenero}")
	public List<SerieDTO> obterSeriesPorCategoria(@PathVariable String nomeGenero) {
		return servico.obterSeriesPorCategoria(nomeGenero);
	}
	
//	@GetMapping("/{id}/temporadas/top")
//	public List<EpisodioDTO> obterTop5Episodios(@PathVariable Long id) {
//		return servico.obterTop5Episodios(id);
//	}
	
}
