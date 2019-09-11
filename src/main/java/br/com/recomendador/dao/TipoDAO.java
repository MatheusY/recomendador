package br.com.recomendador.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.recomendador.entity.Tipo;

public class TipoDAO implements ITipoDAO {

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
	public List<Tipo> searchAll() {
		try {
			CriteriaQuery<Tipo> criteriaQuery = getBuilder().createQuery(Tipo.class);
			Root<Tipo> root = criteriaQuery.from(Tipo.class);
			criteriaQuery.select(root);
			TypedQuery<Tipo> createQuery = entityManager.createQuery(criteriaQuery);
			return createQuery.getResultList();
		} catch (NoResultException e) {
			return new ArrayList<>();
		}
	}

}
