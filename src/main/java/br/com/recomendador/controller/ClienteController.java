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
			List<Avaliacao> avaliacoes = avaliacaoBusiness.buscarRecomendacao();
			geraRecomendacao.geraAvaliacaoCSV(avaliacoes);
		} catch (SystemException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public void salvarCliente() {
		if ("teste".equals(clienteSelecao.getNome()))
			this.setClienteSelecao(clienteBusiness.buscar(2));
		else if("teste2".equals(clienteSelecao.getNome()))
			this.setClienteSelecao(clienteBusiness.buscar(1270));
		else if("teste3".equals(clienteSelecao.getNome()))
			this.setClienteSelecao(clienteBusiness.buscar(1269));
		else if("teste4".equals(clienteSelecao.getNome()))
			this.setClienteSelecao(clienteBusiness.buscar(1268));
		else if("teste5".equals(clienteSelecao.getNome()))
			this.setClienteSelecao(clienteBusiness.buscar(1267));
		else
			this.setClienteSelecao(clienteBusiness.cadastrar(clienteSelecao));
		this.setSessionAttribute(CLIENTE_KEY, this.getClienteSelecao());
//		this.clienteModel.setCliente(cliente);
//		this.clienteBusiness.setCliente(this.getClienteSelecao());
		this.renderizarTela();
	}

	public Cliente getClienteSelecao() {
		return clienteSelecao;
	}

	public void setClienteSelecao(Cliente clienteSelecao) {
		this.clienteSelecao = clienteSelecao;
	}

}
