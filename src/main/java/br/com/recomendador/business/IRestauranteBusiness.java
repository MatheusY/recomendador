package br.com.recomendador.business;

import java.util.List;

import br.com.recomendador.entity.Restaurante;
import br.com.recomendador.entity.Tipo;

public interface IRestauranteBusiness {
	
	public List<Restaurante> buscarTodos();

	public Restaurante buscarPorId(Long id);

	public void closeDialog();

	public List<Restaurante> buscarPorNomeOuTipo(String filtroNome, String filtroTipo);

}
