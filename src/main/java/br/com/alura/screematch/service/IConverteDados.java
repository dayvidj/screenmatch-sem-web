package br.com.alura.screematch.service;

import br.com.alura.screematch.model.Dados;

public interface IConverteDados {
	
	//Método genérico que transforma o json recebido como parâmetro na classe indicada que também foi recebida como parâmetro
	<T> T obterDados(String json, Class <T> classe);
	
}
