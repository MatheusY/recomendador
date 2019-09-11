package br.com.recomendador.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(value=RestauranteTipo.class)
public class RestauranteTipo_ {

	public static volatile SingularAttribute<RestauranteTipo, Long> id;
	
	public static volatile SingularAttribute<RestauranteTipo, Restaurante> restaurante;
	
	public static volatile SingularAttribute<RestauranteTipo, Tipo> tipo;
}
