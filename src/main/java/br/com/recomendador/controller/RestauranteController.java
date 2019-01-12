package br.com.recomendador.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.recomendador.business.IAvaliacaoBusiness;
import br.com.recomendador.business.IClienteBusiness;
import br.com.recomendador.business.IRestauranteBusiness;
import br.com.recomendador.entity.Avaliacao;
import br.com.recomendador.entity.Cliente;
import br.com.recomendador.entity.Restaurante;

@Named
@ViewScoped
public class RestauranteController extends AbstractController {

	private static final long serialVersionUID = -8520499726686174261L;

	/**
	 * 
	 */

	@Inject
	private IRestauranteBusiness restauranteBusiness;

	@Inject
	private IClienteBusiness clienteBusiness;

	@Inject
	private IAvaliacaoBusiness avaliacaoBusiness;

	private static final String RESTAURANTE_KEY = "restauranteSelecao";

	private static final String AVALIACAO_KEY = "avaliacao";
	
	private static final String CLIENTE_KEY = "clienteSelecao";

	private Restaurante restauranteSelecao;

	private Cliente clienteSelecao;

	private Avaliacao avaliacao;

	private int paginaAtual = 1;

	List<Restaurante> restaurantes = new ArrayList<>();

	@PostConstruct
	public void init() {
		this.restaurantes = restauranteBusiness.buscarTodos();
		this.restauranteSelecao = (Restaurante) this.getSessionAttribute(RESTAURANTE_KEY);
		this.avaliacao = (Avaliacao) this.getSessionAttribute(AVALIACAO_KEY);
		this.clienteSelecao = (Cliente) this.getSessionAttribute(CLIENTE_KEY);
		if (avaliacao == null)
			avaliacao = new Avaliacao();
		if (clienteSelecao == null) {
			clienteSelecao = new Cliente();
			clienteSelecao.setNome("Matheus");
			clienteSelecao = clienteBusiness.cadastrar(clienteSelecao);
		}

	}

	public void detalhe() {
		this.setSessionAttribute(RESTAURANTE_KEY, this.restauranteSelecao);
		avaliacao = avaliacaoBusiness.buscarPorRestauranteECliente(restauranteSelecao, clienteSelecao);
		this.setSessionAttribute(AVALIACAO_KEY, this.avaliacao);
		this.setSessionAttribute(CLIENTE_KEY, this.clienteSelecao);
		this.renderizarTela();
	}

	public void salvarAvaliacao() {
		if (avaliacao.getId() == null) {
			avaliacao.setCliente(clienteSelecao);
			avaliacao.setRestaurante(restauranteSelecao);
			avaliacaoBusiness.salvar(avaliacao);
		} else {
			avaliacaoBusiness.editar(avaliacao);
		}
		this.renderizarTela();

	}

	public List<Restaurante> getRestaurantes() {
		return restaurantes;
	}

	public void setRestaurantes(List<Restaurante> restaurantes) {
		this.restaurantes = restaurantes;
	}

	public Restaurante getRestauranteSelecao() {
		return restauranteSelecao;
	}

	public void setRestauranteSelecao(Restaurante restauranteSelecao) {
		this.restauranteSelecao = restauranteSelecao;
	}

	public Avaliacao getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}

	public Cliente getClienteSelecao() {
		return clienteSelecao;
	}

	public void setClienteSelecao(Cliente clienteSelecao) {
		this.clienteSelecao = clienteSelecao;
	}

	public int getPaginaAtual() {
		return paginaAtual;
	}

	public void setPaginaAtual(int paginaAtual) {
		this.paginaAtual = paginaAtual;
	}

}
