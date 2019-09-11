package br.com.recomendador.business;

import java.util.List;

import br.com.recomendador.entity.RestauranteTipo;

public interface IRestauranteTipoBusiness {
	
	public List<RestauranteTipo> buscaTodos();
}
