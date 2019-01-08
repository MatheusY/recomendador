package br.com.recomendador.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(value = Avaliacao.class)
public class Avaliacao_ {

	public static volatile SingularAttribute<Avaliacao, Long> id;
	
	public static volatile SingularAttribute<Avaliacao, Integer> nota;
	
	public static volatile SingularAttribute<Avaliacao, Cliente> cliente;
	
	public static volatile SingularAttribute<Avaliacao, Restaurante> restaurante;
}
