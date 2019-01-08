package br.com.recomendador.business;

import br.com.recomendador.entity.Avaliacao;
import br.com.recomendador.entity.Cliente;
import br.com.recomendador.entity.Restaurante;

public interface IAvaliacaoBusiness {

	public Avaliacao salvar(Avaliacao avaliacao);

	public Avaliacao buscarPorRestauranteECliente(Restaurante restaurante, Cliente cliente);

	public Avaliacao editar(Avaliacao avaliacao);
	
}