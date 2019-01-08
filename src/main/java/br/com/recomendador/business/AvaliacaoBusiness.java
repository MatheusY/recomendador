package br.com.recomendador.business;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.recomendador.dao.IAvaliacaoDAO;
import br.com.recomendador.entity.Avaliacao;
import br.com.recomendador.entity.Cliente;
import br.com.recomendador.entity.Restaurante;

@Stateless
public class AvaliacaoBusiness implements IAvaliacaoBusiness {
	
	@Inject
	private IAvaliacaoDAO avaliacaoDAO;

	@Override
	public Avaliacao salvar(Avaliacao avaliacao) {
		return avaliacaoDAO.insert(avaliacao);
	}

	@Override
	public Avaliacao buscarPorRestauranteECliente(Restaurante restaurante, Cliente cliente) {
		return avaliacaoDAO.searchByRestaurantAndClient(restaurante, cliente);
	}

	@Override
	public Avaliacao editar(Avaliacao avaliacao) {
		return avaliacaoDAO.update(avaliacao);
	}


}
