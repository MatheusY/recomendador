package br.com.recomendador.dao;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.recomendador.entity.Cliente;
import br.com.recomendador.entity.Cliente_;

public class ClienteDAO implements IClienteDAO {

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
	public Cliente searchById(long id) {
		CriteriaQuery<Cliente> criteriaQuery = getBuilder().createQuery(Cliente.class);
		Root<Cliente> root = criteriaQuery.from(Cliente.class);
		Predicate predicateId = getBuilder().equal(root.get(Cliente_.id), id);
		criteriaQuery.where(predicateId);
		TypedQuery<Cliente> typedQuery = entityManager.createQuery(criteriaQuery);
		return typedQuery.getSingleResult();
	}

}
