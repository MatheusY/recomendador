package br.com.recomendador.dao;

import java.util.List;

import br.com.recomendador.entity.RestauranteTipo;

public interface IRestauranteTipoDAO {

	List<RestauranteTipo> searchAll();
	
}
