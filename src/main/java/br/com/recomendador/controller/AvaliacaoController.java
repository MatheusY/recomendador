package br.com.recomendador.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.recomendador.business.IAvaliacaoBusiness;
import br.com.recomendador.entity.Avaliacao;
import br.com.recomendador.entity.Cliente;

@Named
@ViewScoped
public class AvaliacaoController extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 863959190730661706L;

	private final String CLIENTE_KEY = "clienteSelecao";

	@Inject
	private IAvaliacaoBusiness avaliacaoBusiness;

	private Cliente clienteSelecao;

	private List<Avaliacao> avaliacoes;

	@PostConstruct
	public void init() {
		clienteSelecao = (Cliente) getSessionAttribute(CLIENTE_KEY);
		
		if(clienteSelecao.getId() != null)
			avaliacoes = avaliacaoBusiness.buscarPorCliente(clienteSelecao);
		
	}

	public IAvaliacaoBusiness getAvaliacaoBusiness() {
		return avaliacaoBusiness;
	}

	public void setAvaliacaoBusiness(IAvaliacaoBusiness avaliacaoBusiness) {
		this.avaliacaoBusiness = avaliacaoBusiness;
	}

	public Cliente getClienteSelecao() {
		return clienteSelecao;
	}

	public void setClienteSelecao(Cliente clienteSelecao) {
		this.clienteSelecao = clienteSelecao;
	}

	public List<Avaliacao> getAvaliacoes() {
		return avaliacoes;
	}

	public void setAvaliacoes(List<Avaliacao> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}

}
