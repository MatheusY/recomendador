package br.com.recomendador.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.recomendador.entity.Restaurante;

public class RestauranteDAO implements IRestauranteDAO{
	
	@PersistenceContext
	protected EntityManager entityManager;
	
	private CriteriaBuilder builder;
	
	@PostConstruct
	private void init() {
		builder = entityManager.getCriteriaBuilder();
	}
	
	public CriteriaBuilder getBuilder() {
		return this.builder;
	}

	@Override
	public List<Restaurante> findAll() {
		CriteriaQuery<Restaurante> criteriaQuery = getBuilder().createQuery(Restaurante.class);
		Root<Restaurante> root = criteriaQuery.from(Restaurante.class);
		criteriaQuery.select(root);
		TypedQuery<Restaurante> typedQuery = entityManager.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

}
