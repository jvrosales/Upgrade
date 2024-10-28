/**
 * Generic facade for CRUD Operations
 * @version 1.0, 09/10/2018
 * @author Excelencia Corpotativa
 * @author Juan Rodriguez <jr_rodriguez_l@hotmail.com>
 * @author Mauricio Quiridunbay <quirid@gmail.com> 
 */

package ec.gob.ambiente.ide.facade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public abstract class AbstractFacadeModel<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "idePU")
	private EntityManager em;

	private final Class<T> entityClass;
	private List<Predicate> wherePredicates = new ArrayList<>();
	private List<Order> orderByPredicates = new ArrayList<>();

	private CriteriaBuilder criteriaBuilder;
	private CriteriaQuery<T> criteria;
	private Root<T> record;

	public AbstractFacadeModel(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public abstract void configure(String filter);

	public boolean create(T entity) {
		try {
			em.persist(entity);
			em.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean save(T entity) {
		try {
			em.merge(entity);
			em.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean delete(T entity) {
		try {
			em.remove(em.merge(entity));
			em.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean createNoFlush(T entity) {
		try {
			em.persist(entity);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean saveNoFlush(T entity) {
		try {
			em.merge(entity);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteNoFlush(T entity) {
		try {
			em.remove(em.merge(entity));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean flush() {
		try {
			em.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void initialize() {
		this.criteriaBuilder = em.getCriteriaBuilder();
		this.criteria = criteriaBuilder.createQuery(entityClass);
		this.record = criteria.from(entityClass);
	}

	public T findById(Long id) {
		try {
			return em.find(entityClass, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllGeneric() {
		try {
			return buildQuery().getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> findAllGeneric(int firstResult, int maxResults) {
		try {
			Query query = buildQuery();
			query.setFirstResult(firstResult);
			query.setMaxResults(maxResults);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<T> findAll() {
		try {
			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<T> criteria = criteriaBuilder.createQuery(entityClass);
			Root<T> record = criteria.from(entityClass);
			criteria.select(record);
			return em.createQuery(criteria).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Query buildQuery() {
		getCriteria().select(getRecord())
				.where((Predicate[]) getWherePredicates().toArray(new Predicate[getWherePredicates().size()]))
				.orderBy((Order[]) getOrderByPredicates().toArray(new Order[getOrderByPredicates().size()]));
		return em.createQuery(criteria);
	}

	public long count() {
		try {
			CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
			CriteriaQuery<Long> cQuery = builder.createQuery(Long.class);
			Root<T> from = cQuery.from(entityClass);
			CriteriaQuery<Long> select = cQuery.select(builder.count(from))
					.where((Predicate[]) getWherePredicates().toArray(new Predicate[getWherePredicates().size()]));
			TypedQuery<Long> tq = getEntityManager().createQuery(select);
			return tq.getSingleResult().intValue();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	// Metodos para control de predicados y ordenes

	public void clearWherePredicates() {
		setWherePredicates(new ArrayList<Predicate>());
	}

	public void clearOrderByPredicates() {
		setOrderByPredicates(new ArrayList<Order>());
	}

	public void addWherePredicate(Predicate predicate) {
		getWherePredicates().add(predicate);
	}

	public void addOrderByPredicate(Order order) {
		getOrderByPredicates().add(order);
	}

	public boolean isTransient(T object) {
		if (object != null) {
			Object objecjectID = getIdentifier(object);
			if (objecjectID == null)
				return true;
			else if (objecjectID.toString().equals("0"))
				return true;
			return false;
		}
		return true;
	}

	public Object getIdentifier(T object) {
		return getEntityManager().getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(object);
	}

	// Getters y Setters

	public EntityManager getEntityManager() {
		return em;
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}

	public List<Predicate> getWherePredicates() {
		return wherePredicates;
	}

	public void setWherePredicates(List<Predicate> wherePredicates) {
		this.wherePredicates = wherePredicates;
	}

	public List<Order> getOrderByPredicates() {
		return orderByPredicates;
	}

	public void setOrderByPredicates(List<Order> orderByPredicates) {
		this.orderByPredicates = orderByPredicates;
	}

	public CriteriaQuery<T> getCriteria() {
		return criteria;
	}

	public void setCriteria(CriteriaQuery<T> criteria) {
		this.criteria = criteria;
	}

	public Root<T> getRecord() {
		return record;
	}

	public void setRecord(Root<T> record) {
		this.record = record;
	}

	public CriteriaBuilder getCriteriaBuilder() {
		return criteriaBuilder;
	}
}
