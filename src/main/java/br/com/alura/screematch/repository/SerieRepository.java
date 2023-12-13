package br.com.alura.screematch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.screematch.model.Serie;

public interface SerieRepository extends JpaRepository<Serie, Long>{
	
}
