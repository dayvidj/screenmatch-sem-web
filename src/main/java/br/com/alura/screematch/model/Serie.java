package br.com.alura.screematch.model;

import java.util.OptionalDouble;

import br.com.alura.screematch.enums.Categoria;

public class Serie {
	private String titulo;
	private Categoria genero;
	private String atores;
	private String poster;
	private String sinopse;
	private Double avaliacao;
	private Integer totalTemporadas;
	
	public Serie(Dados dadosSerie) {
		this.titulo = dadosSerie.titulo();
		this.totalTemporadas = dadosSerie.totalTemporadas();
		this.avaliacao = OptionalDouble.of(Double.valueOf(dadosSerie.avaliacao())).orElse(0); 
		this.genero = Categoria.fromString(dadosSerie.genero().split(",")[0].trim());
		this.atores = dadosSerie.atores();
		this.poster = dadosSerie.poster();
		this.sinopse = dadosSerie.sinopse();
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Categoria getGenero() {
		return genero;
	}

	public void setGenero(Categoria genero) {
		this.genero = genero;
	}

	public String getAtores() {
		return atores;
	}

	public void setAtores(String atores) {
		this.atores = atores;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getSinopse() {
		return sinopse;
	}

	public void setSinopse(String sinopse) {
		this.sinopse = sinopse;
	}

	public Double getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Double avaliacao) {
		this.avaliacao = avaliacao;
	}

	public Integer getTotalTemporadas() {
		return totalTemporadas;
	}

	public void setTotalTemporadas(Integer totalTemporadas) {
		this.totalTemporadas = totalTemporadas;
	}

	@Override
	public String toString() {
		return "titulo=" + titulo + 
				", genero=" + genero + 
				", atores=" + atores + 
				", poster=" + poster
				+ ", sinopse=" + sinopse + 
				", avaliacao=" + avaliacao + 
				", totalTemporadas=" + totalTemporadas;
	}
	
	
	
}
