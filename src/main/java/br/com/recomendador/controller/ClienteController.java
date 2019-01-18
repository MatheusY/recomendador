package br.com.recomendador.controller;

import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.recomendador.business.IClienteBusiness;
import br.com.recomendador.entity.Cliente;

@Named
@SessionScoped
public class ClienteController extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8524435240290097535L;

	private Cliente clienteSelecao = new Cliente();
	
	private static final String CLIENTE_KEY ="clienteSelecao";

	@Inject
	private IClienteBusiness clienteBusiness;

	public void salvarCliente() {
		this.setClienteSelecao(clienteBusiness.cadastrar(this.clienteSelecao));
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
