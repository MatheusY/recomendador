package br.com.recomendador.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.recomendador.dao.IAvaliacaoDAO;
import br.com.recomendador.entity.Avaliacao;
import br.com.recomendador.entity.Cliente;
import br.com.recomendador.entity.Restaurante;
import br.com.recomendador.exception.SystemException;
import br.com.recomendador.main.GerarRecomendacao;

@Stateless
public class AvaliacaoBusiness implements IAvaliacaoBusiness {

	@Inject
	private IAvaliacaoDAO avaliacaoDAO;

	private GerarRecomendacao geraRecomendacao = new GerarRecomendacao();

	@Override
	public Avaliacao salvar(Avaliacao avaliacao) throws SystemException {
		avaliacaoDAO.insert(avaliacao);
		geraRecomendacao.adicionaAvaliacao(avaliacao);
		return avaliacao;

	}

	@Override
	public Avaliacao buscarPorRestauranteECliente(Restaurante restaurante, Cliente cliente) {
		return avaliacaoDAO.searchByRestaurantAndClient(restaurante, cliente);
	}

	@Override
	public Avaliacao editar(Avaliacao avaliacao) throws SystemException {
		Avaliacao avaliacaoBanco = avaliacaoDAO.searchByRestaurantAndClient(avaliacao.getRestaurante(),
				avaliacao.getCliente());
		if (avaliacaoBanco.getNota() != avaliacao.getNota()) {
			avaliacaoDAO.update(avaliacao);
			List<Avaliacao> avaliacoes = buscarTodos();
			geraRecomendacao.geraAvaliacaoCSV(avaliacoes);
		}
		return avaliacao;
	}

	@Override
	public List<Avaliacao> buscarTodos() {
		return avaliacaoDAO.searchAll();
	}

	@Override
	public List<Avaliacao> buscarPorCliente(Cliente cliente) {
		return avaliacaoDAO.searchByCliente(cliente);
	}

}
