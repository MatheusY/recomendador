package br.com.recomendador.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.recomendador.entity.RestauranteTipo;

public class RestauranteTipoDAO implements IRestauranteTipoDAO {
	
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
	public List<RestauranteTipo> searchAll() {
		CriteriaQuery<RestauranteTipo> criteriaQuery = getBuilder().createQuery(RestauranteTipo.class);
		Root<RestauranteTipo> root = criteriaQuery.from(RestauranteTipo.class);
		criteriaQuery.select(root);
		TypedQuery<RestauranteTipo> createQuery = entityManager.createQuery(criteriaQuery);
		return createQuery.getResultList();
	}

}
