package br.com.alura.screematch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Dados(@JsonAlias("Title") String titulo,
					@JsonAlias("Genre") String genero,
					@JsonAlias("Actors") String atores,
					@JsonAlias("Poster") String poster,
					@JsonAlias("Plot") String sinopse,
					@JsonAlias("imdbRating") String avaliacao,
					@JsonAlias("totalSeasons") Integer totalTemporadas) {
}
