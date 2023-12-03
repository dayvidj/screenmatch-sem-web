package br.com.alura.screematch.service;


public interface IConverteDados {
	
	//Método genérico que transforma o json recebido como parâmetro na classe indicada que também foi recebida como parâmetro
	<T> T obterDados(String json, Class <T> classe);
	
}
