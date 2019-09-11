package br.com.recomendador.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.recomendador.dao.IRestauranteTipoDAO;
import br.com.recomendador.entity.RestauranteTipo;

@Stateless
public class RestauranteTIpoBusiness implements IRestauranteTipoBusiness {
	
	@Inject
	private IRestauranteTipoDAO restauranteTipoDAO;

	@Override
	public List<RestauranteTipo> buscaTodos() {
		return restauranteTipoDAO.searchAll();
	}

}
