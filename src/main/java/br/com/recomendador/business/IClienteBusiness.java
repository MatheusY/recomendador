package br.com.recomendador.business;

import br.com.recomendador.entity.Cliente;

public interface IClienteBusiness {

	public Cliente buscar(long id);
	
	public Cliente cadastrar(Cliente cliente);
	
	public Cliente getCliente();
	
	public void setCliente(Cliente cliente);

}
