package br.com.recomendador.entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(value = Cliente.class)
public class Cliente_ {
	
	public static volatile SingularAttribute<Cliente, Long> id;
	
	public static volatile SingularAttribute<Cliente, String> nome;
	
	public static volatile ListAttribute<Cliente, Avaliacao> avaliacoes;
}
