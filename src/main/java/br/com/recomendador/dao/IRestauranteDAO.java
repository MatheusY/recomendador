package br.com.recomendador.dao;

import java.util.List;

import br.com.recomendador.entity.Restaurante;

public interface IRestauranteDAO {

	public List<Restaurante> findAll();

	public Restaurante findById(Long id);

	public List<Restaurante> findByNameOrType(String filtroNome, String filtroTipo);

	public List<String> findTipo();

}
