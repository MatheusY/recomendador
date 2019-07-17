package br.com.recomendador.business;

import java.util.List;

import br.com.recomendador.entity.Avaliacao;
import br.com.recomendador.entity.Cliente;
import br.com.recomendador.entity.Restaurante;
import br.com.recomendador.exception.SystemException;

public interface IAvaliacaoBusiness {

	public Avaliacao salvar(Avaliacao avaliacao) throws SystemException;

	public Avaliacao buscarPorRestauranteECliente(Restaurante restaurante, Cliente cliente);

	public Avaliacao editar(Avaliacao avaliacao) throws SystemException;

	public List<Avaliacao> buscarTodos();

	public List<Avaliacao> buscarPorCliente(Cliente cliente);
	
}
