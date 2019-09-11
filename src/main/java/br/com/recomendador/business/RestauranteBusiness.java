package br.com.recomendador.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;

import br.com.recomendador.dao.IRestauranteDAO;
import br.com.recomendador.entity.Restaurante;
import br.com.recomendador.entity.Tipo;

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
		if(filtroNome != null) {
			filtroNome = filtroNome.toLowerCase();
			if(filtroTipo == null)
				return buscaPorNome(filtroNome);
		}
		else if(filtroTipo == null)
			return buscarTodos();
		return restauranteDAO.findByNameOrType(filtroNome, filtroTipo);
	}


	private List<Restaurante>buscaPorNome(String filtroNome) {
		return restauranteDAO.findByName(filtroNome);
	}
	
}
