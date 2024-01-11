package br.com.alura.screematch.dto;

import br.com.alura.screematch.enums.Categoria;

public record SerieDTO(Long id, 
						String titulo, 
						Integer totalTemporadas,
						Double avaliacao,
						Categoria genero, 
						String atores, 
						String poster, 
						String sinopse) {
}
