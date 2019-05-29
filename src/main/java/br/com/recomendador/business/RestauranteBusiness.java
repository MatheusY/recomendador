package br.com.recomendador.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;

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

	@Override
	public Restaurante buscarPorId(Long id) {
		return restauranteDAO.findById(id);
	}

	@Override
	public void closeDialog() {
		RequestContext.getCurrentInstance().closeDialog(null);
		
	}

	@Override
	public List<Restaurante> buscarPorNomeOuTipo(String filtroNome, String filtroTipo) {
		if(filtroNome != null)
			filtroNome = filtroNome.toLowerCase();
		return restauranteDAO.findByNameOrType(filtroNome, filtroTipo);
	}

	@Override
	public List<String> buscarTipo() {
		return restauranteDAO.findTipo();
	}

}
