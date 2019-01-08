package br.com.recomendador.dao;

import br.com.recomendador.entity.Cliente;

public interface IClienteDAO {

	public Cliente searchById(long id);

}
