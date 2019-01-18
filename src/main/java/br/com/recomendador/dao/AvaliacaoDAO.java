package br.com.recomendador.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.recomendador.entity.Avaliacao;
import br.com.recomendador.entity.Avaliacao_;
import br.com.recomendador.entity.Cliente;
import br.com.recomendador.entity.Restaurante;

public class AvaliacaoDAO implements IAvaliacaoDAO {

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
	public Avaliacao insert(Avaliacao avaliacao) {
		entityManager.persist(avaliacao);
		entityManager.flush();
		return avaliacao;
	}

	@Override
	public Avaliacao searchByRestaurantAndClient(Restaurante restaurante, Cliente cliente) {
		try {
			CriteriaQuery<Avaliacao> criteriaQuery = getBuilder().createQuery(Avaliacao.class);
			Root<Avaliacao> root = criteriaQuery.from(Avaliacao.class);
			Predicate predicateRestaurante = getBuilder().equal(root.get(Avaliacao_.restaurante), restaurante);
			Predicate predicateCliente = getBuilder().equal(root.get(Avaliacao_.cliente), cliente);
			Predicate predicateFinal = getBuilder().and(predicateRestaurante, predicateCliente);
			criteriaQuery.where(predicateFinal);
			TypedQuery<Avaliacao> typedQuery = entityManager.createQuery(criteriaQuery);
			return typedQuery.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Avaliacao update(Avaliacao avaliacao) {
		avaliacao = entityManager.merge(avaliacao);
		entityManager.flush();
		return avaliacao;
	}

	@Override
	public List<Avaliacao> searchAll() {
		CriteriaQuery<Avaliacao> criteriaQuery = getBuilder().createQuery(Avaliacao.class);
		Root<Avaliacao> root = criteriaQuery.from(Avaliacao.class);
		criteriaQuery.select(root);
		TypedQuery<Avaliacao> typedQuery = entityManager.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@Override
	public List<Avaliacao> searchByCliente(Cliente cliente) {
		CriteriaQuery<Avaliacao> criteriaQuery = getBuilder().createQuery(Avaliacao.class);
		Root<Avaliacao> root = criteriaQuery.from(Avaliacao.class);
		Predicate predicate = getBuilder().equal(root.get(Avaliacao_.cliente), cliente);
		criteriaQuery.where(predicate);
		TypedQuery<Avaliacao> typedQuery = entityManager.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

}
