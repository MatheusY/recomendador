package br.com.recomendador.dao;

import java.util.List;

import br.com.recomendador.entity.Restaurante;
import br.com.recomendador.entity.Tipo;

public interface IRestauranteDAO {

	public List<Restaurante> findAll();

	public Restaurante findById(Long id);

	public List<Restaurante> findByNameOrType(String filtroNome, String filtroTipo);

	public List<Restaurante> findByName(String filtroNome);

}
