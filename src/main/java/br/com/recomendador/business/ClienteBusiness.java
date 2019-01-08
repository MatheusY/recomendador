package br.com.recomendador.business;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.recomendador.dao.IAvaliacaoDAO;
import br.com.recomendador.dao.IClienteDAO;
import br.com.recomendador.entity.Cliente;

@Stateless
public class ClienteBusiness implements IClienteBusiness {
	
	@Inject
	private IClienteDAO clienteDAO;

	@Override
	public Cliente buscar(long id) {
		return clienteDAO.searchById(id);
		
	}


}
