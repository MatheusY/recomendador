package br.com.recomendador.dao;

import java.util.List;

import br.com.recomendador.entity.Restaurante;

public interface IRestauranteDAO {

	public List<Restaurante> findAll();

	public Restaurante findById(Long id);

}
