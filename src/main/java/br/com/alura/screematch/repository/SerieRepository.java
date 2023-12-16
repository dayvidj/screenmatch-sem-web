package br.com.alura.screematch.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.screematch.enums.Categoria;
import br.com.alura.screematch.model.Serie;

public interface SerieRepository extends JpaRepository<Serie, Long> {
	Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);

	List<Serie> findByAtoresContainingIgnoreCase(String nomeAutor);

	List<Serie> findTop2ByOrderByAvaliacaoDesc(); 
	
	List<Serie> findByGenero(Categoria categoria);
	
	List<Serie> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(Integer totalTemporadas, Double avaliacao);
}
