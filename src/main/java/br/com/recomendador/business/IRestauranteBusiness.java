package br.com.recomendador.business;

import java.util.List;

import br.com.recomendador.entity.Restaurante;

public interface IRestauranteBusiness {
	
	public List<Restaurante> buscarTodos();

}
