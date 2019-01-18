package br.com.recomendador.business;

import java.util.List;

import br.com.recomendador.entity.Avaliacao;
import br.com.recomendador.entity.Cliente;
import br.com.recomendador.entity.Restaurante;

public interface IAvaliacaoBusiness {

	public Avaliacao salvar(Avaliacao avaliacao);

	public Avaliacao buscarPorRestauranteECliente(Restaurante restaurante, Cliente cliente);

	public Avaliacao editar(Avaliacao avaliacao);

	public List<Avaliacao> buscarTodos();

	public List<Avaliacao> buscarPorCliente(Cliente cliente);
	
}
