package br.com.recomendador.dao;

import java.util.List;

import br.com.recomendador.entity.Avaliacao;
import br.com.recomendador.entity.Cliente;
import br.com.recomendador.entity.Restaurante;

public interface IAvaliacaoDAO {

	public Avaliacao insert(Avaliacao avaliacao);

	public Avaliacao searchByRestaurantAndClient(Restaurante restaurante, Cliente cliente);

	public Avaliacao update(Avaliacao avaliacao);

	public List<Avaliacao> searchAll();

}
