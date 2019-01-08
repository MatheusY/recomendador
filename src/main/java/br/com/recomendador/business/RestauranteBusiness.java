package br.com.recomendador.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.recomendador.dao.IRestauranteDAO;
import br.com.recomendador.entity.Restaurante;

@Stateless
public class RestauranteBusiness implements IRestauranteBusiness {
	
	@Inject
	private IRestauranteDAO restauranteDAO;

	@Override
	public List<Restaurante> buscarTodos() {
		return restauranteDAO.findAll();
	}

}
