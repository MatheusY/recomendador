package br.com.recomendador.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.recomendador.business.IAvaliacaoBusiness;
import br.com.recomendador.business.IClienteBusiness;
import br.com.recomendador.entity.Avaliacao;
import br.com.recomendador.entity.Cliente;
import br.com.recomendador.exception.SystemException;
import br.com.recomendador.main.GerarRecomendacao;

@Named
@ViewScoped
public class ClienteController extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8524435240290097535L;

	private Cliente clienteSelecao = new Cliente();

	private static final String CLIENTE_KEY = "clienteSelecao";

	@Inject
	private IClienteBusiness clienteBusiness;

	@Inject
	private IAvaliacaoBusiness avaliacaoBusiness;

	private GerarRecomendacao geraRecomendacao;
	
	@Inject
	private ClienteModel clienteModel;

	@PostConstruct
	public void init() {
		try {
			geraRecomendacao = new GerarRecomendacao();
			List<Avaliacao> avaliacoes = avaliacaoBusiness.buscarTodos();
			geraRecomendacao.geraAvaliacaoCSV(avaliacoes);
		} catch (SystemException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public void salvarCliente() {
		if ("teste".equals(clienteSelecao.getNome()))
			this.setClienteSelecao(clienteBusiness.buscar(1271));
//			clienteModel.setCliente(clienteBusiness.buscar(1271));
		else
			this.setClienteSelecao(clienteBusiness.cadastrar(clienteSelecao));
		this.setSessionAttribute(CLIENTE_KEY, this.getClienteSelecao());
		this.renderizarTela();
	}

	public Cliente getClienteSelecao() {
		return clienteSelecao;
	}

	public void setClienteSelecao(Cliente clienteSelecao) {
		this.clienteSelecao = clienteSelecao;
	}

}
