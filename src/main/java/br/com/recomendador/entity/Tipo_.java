package br.com.recomendador.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(value=Tipo.class)
public class Tipo_ {
	
	public static volatile SingularAttribute<Tipo, Long> id;
	
	public static volatile SingularAttribute<Tipo, String> nome;
}
