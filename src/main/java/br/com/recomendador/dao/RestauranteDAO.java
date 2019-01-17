package br.com.recomendador.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.recomendador.entity.Restaurante;
import br.com.recomendador.entity.Restaurante_;

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
		criteriaQuery.orderBy(getBuilder().asc(root.get(Restaurante_.nome)));
		TypedQuery<Restaurante> typedQuery = entityManager.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@Override
	public Restaurante findById(Long id) {
		CriteriaQuery<Restaurante> criteriaQuery = getBuilder().createQuery(Restaurante.class);
		Root<Restaurante> root = criteriaQuery.from(Restaurante.class);
		Predicate predicate = getBuilder().equal(root.get(Restaurante_.id), id);
		criteriaQuery.where(predicate);
		TypedQuery<Restaurante> typedQuery = entityManager.createQuery(criteriaQuery);
		return typedQuery.getSingleResult();
	}

}
