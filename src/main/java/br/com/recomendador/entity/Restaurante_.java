package br.com.recomendador.entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(value = Restaurante.class)
public class Restaurante_ {
	
	public static volatile SingularAttribute<Restaurante, Long> id;
	
	public static volatile SingularAttribute<Restaurante, String> nome;
	
	public static volatile SingularAttribute<Restaurante, String> endereco;
	
	public static volatile SingularAttribute<Restaurante, String> imagem;
	
	public static volatile ListAttribute<Restaurante, RestauranteTipo> restauranteTipos;
	
	public static volatile ListAttribute<Restaurante, Avaliacao> avaliacoes;
}
