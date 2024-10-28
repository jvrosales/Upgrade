package ec.gob.ambiente.ide.facade;

/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html
 */

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

/**
 * 
 * @author tgiunipero
 */
public abstract class AbstractFacade<T, E> {

	private Class<T> entityClass;
	@SuppressWarnings("unused")
	private Class<E> primaryKeyClass;
  
	@PersistenceContext(unitName = "idePU")
	private EntityManager em;

	protected EntityManager getEntityManager() {
		return em;
	}

	public AbstractFacade(Class<T> entityClass, Class<E> primaryKeyClass) {
		this.entityClass = entityClass;
		this.primaryKeyClass = primaryKeyClass;
	}

	public T find(E id) {
		return getEntityManager().find(entityClass, id);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<T> findAll() {
		javax.persistence.criteria.CriteriaQuery cq = getEntityManager()
				.getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		return getEntityManager().createQuery(cq).getResultList();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<T> findRange(int[] range) {
		javax.persistence.criteria.CriteriaQuery cq = getEntityManager()
				.getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		javax.persistence.Query q = getEntityManager().createQuery(cq);
		q.setMaxResults(range[1] - range[0]);
		q.setFirstResult(range[0]);
		return q.getResultList();
	}

	public int count() {
		CriteriaQuery<Object> cq = getEntityManager()
				.getCriteriaBuilder().createQuery();
		javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
		cq.select(getEntityManager().getCriteriaBuilder().count(rt));
		javax.persistence.Query q = getEntityManager().createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}
	
	

}
