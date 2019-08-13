package br.com.recomendador.business;

import javax.ejb.Stateful;
import javax.inject.Inject;

import br.com.recomendador.dao.IClienteDAO;
import br.com.recomendador.entity.Cliente;

@Stateful
public class ClienteBusiness implements IClienteBusiness {
	
	@Inject
	private IClienteDAO clienteDAO;
	
	private Cliente cliente;

	@Override
	public Cliente buscar(long id) {
		return clienteDAO.searchById(id);
		
	}

	@Override
	public Cliente cadastrar(Cliente cliente) {
		return clienteDAO.insert(cliente);
	}

	@Override
	public Cliente getCliente() {
		return cliente;
	}

	@Override
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	

}
