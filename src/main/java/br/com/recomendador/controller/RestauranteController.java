package br.com.recomendador.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.recomendador.business.IAvaliacaoBusiness;
import br.com.recomendador.business.IClienteBusiness;
import br.com.recomendador.business.IRestauranteBusiness;
import br.com.recomendador.entity.Avaliacao;
import br.com.recomendador.entity.Cliente;
import br.com.recomendador.entity.Restaurante;
import br.com.recomendador.main.GerarRecomendacao;

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

	private static final String RECOMENDA_KEY = "recomenda";

	private Restaurante restauranteSelecao;

	private Cliente clienteSelecao;

	private Avaliacao avaliacao;

	private GerarRecomendacao geraRecomendacao;

	private int paginaAtual = 1;
	
	private String filtroNome;

	List<Restaurante> restaurantes = new ArrayList<>();

	List<Restaurante> restaurantesRecomendados = new ArrayList<>();

	private Boolean recomenda = false;
	
	private String tipo;
	
	private List<String> tipos;
	

	@PostConstruct
	public void init() {
		this.restauranteSelecao = (Restaurante) this.getSessionAttribute(RESTAURANTE_KEY);
		this.avaliacao = (Avaliacao) this.getSessionAttribute(AVALIACAO_KEY);
		this.clienteSelecao = (Cliente) this.getSessionAttribute(CLIENTE_KEY);
		
		if(restauranteSelecao == null)
			this.restaurantes = restauranteBusiness.buscarTodos();
		
		if (this.getSessionAttribute(RECOMENDA_KEY) != null) 
			recomenda = (Boolean) this.getSessionAttribute(RECOMENDA_KEY);

		if (recomenda)
			this.procurarRecomendacao();

		if (avaliacao == null)
			avaliacao = new Avaliacao();
				
		this.limparSessionAttribute(RECOMENDA_KEY);
		
	}

	public void detalhe() {
		this.setSessionAttribute(RESTAURANTE_KEY, this.restauranteSelecao);
		avaliacao = avaliacaoBusiness.buscarPorRestauranteECliente(restauranteSelecao, clienteSelecao);
		this.setSessionAttribute(AVALIACAO_KEY, this.avaliacao);
		this.renderizarTela();
	}

	public void detalheDialog() {
		this.fecharDialog();
		this.detalhe();
	}

	public void salvarAvaliacao() {
		if (this.getAvaliacao().getNota() != null) {
			if (avaliacao.getId() == null) {
				avaliacao.setCliente(clienteSelecao);
				avaliacao.setRestaurante(restauranteSelecao);
				avaliacaoBusiness.salvar(avaliacao);
			} else {
				avaliacaoBusiness.editar(avaliacao);
			}
			this.limparSessionAttribute(RESTAURANTE_KEY);
			this.limparSessionAttribute(AVALIACAO_KEY);
			this.renderizarTela();
		}else {
			this.mensagemErro("Insira a nota!");
		}

	}


	public void recomendar(ActionEvent ae) {
		List<Avaliacao> avaliacoes = this.avaliacaoBusiness.buscarPorCliente(this.clienteSelecao); 
		if(avaliacoes.size() > 1) {
		this.setSessionAttribute(RECOMENDA_KEY, Boolean.TRUE);
		RequestContext.getCurrentInstance().openDialog("recomendacao", getDialogOptions(), null);
		}else {
			this.mensagemErro("Para a recomendação ser feita é necessário ter mais de uma avaliação!");
		}
	}

	private void procurarRecomendacao() {
		try {
			geraRecomendacao = new GerarRecomendacao();
			List<Avaliacao> avaliacoes = avaliacaoBusiness.buscarTodos();
			geraRecomendacao.GeraAvaliacaoCSV(avaliacoes);
			List<Long> listaId = geraRecomendacao.geraRecomendacao(clienteSelecao.getId());
			for (Long id : listaId) {
				Restaurante restaurante = this.procurarRestaurante(id);
				this.restaurantesRecomendados.add(restaurante);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void cancelar() {
		this.limparSessionAttribute(AVALIACAO_KEY);
		this.limparSessionAttribute(RESTAURANTE_KEY);
		this.renderizarTela();
	}

	public Restaurante procurarRestaurante(Long id) {
		return restauranteBusiness.buscarPorId(id);
	}

	private Map<String, Object> getDialogOptions() {
		Map<String, Object> options = new HashMap<>();
		options.put("resizable", false);
		options.put("draggable", true);
		options.put("modal", true);
		options.put("height", 220);
		options.put("width", 900);
		options.put("contentHeight", "100%");
		options.put("contentWidth", "100%");
		return options;
	}

	private void fecharDialog() {
		restauranteBusiness.closeDialog();
	}
	
	public void filtrarLista() {
		this.setRestaurantes(restauranteBusiness.buscarPorNomeOuTipo(this.getFiltroNome(), this.getTipo()));
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

	public List<Restaurante> getRestaurantesRecomendados() {
		return restaurantesRecomendados;
	}

	public void setRestaurantesRecomendados(List<Restaurante> restaurantesRecomendados) {
		this.restaurantesRecomendados = restaurantesRecomendados;
	}

	public String getFiltroNome() {
		return filtroNome;
	}

	public void setFiltroNome(String filtroNome) {
		this.filtroNome = filtroNome;
	}

	public List<String> getTipos() {
		return restauranteBusiness.buscarTipo();
	}

	public void setTipos(List<String> tipos) {
		this.tipos = tipos;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
