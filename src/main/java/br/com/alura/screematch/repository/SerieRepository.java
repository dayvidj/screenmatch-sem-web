package br.com.alura.screematch.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.alura.screematch.enums.Categoria;
import br.com.alura.screematch.model.Episodio;
import br.com.alura.screematch.model.Serie;

public interface SerieRepository extends JpaRepository<Serie, Long> {
	Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);

	List<Serie> findByAtoresContainingIgnoreCase(String nomeAutor);

	List<Serie> findTop5ByOrderByAvaliacaoDesc(); 
	
	List<Serie> findByGenero(Categoria categoria);
	
//	List<Serie> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(Integer totalTemporadas, Double avaliacao);
	
	@Query("select s from Serie s WHERE s.totalTemporadas <= :totalTemporadas AND s.avaliacao >= :avaliacao")
	List<Serie> seriesPorTemporadaEAvaliacao(int totalTemporadas, double avaliacao);

	@Query("select e from Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:trechoEpisodio")
	List<Episodio> EpisodiosPorTrecho(String trechoEpisodio);

	@Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.avaliacao DESC LIMIT 5")
	List<Episodio> topEpisodiosPorSerie(Serie serie);
	
	@Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie AND YEAR(e.dataLancamento) >= :anoLancamento")
	List<Episodio> episodiosPorSerieEAno(Serie serie, int anoLancamento);
	
	@Query("SELECT s FROM Serie s JOIN s.episodios e GROUP BY s ORDER BY MAX(e.dataLancamento) DESC LIMIT 5")
    List<Serie> encontrarEpisodiosMaisRecentes();
	
    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.id = :id AND e.temporada = :numero")
	List<Episodio> obterEpisodiosPorTemporada(Long id, Long numero);
	
}
