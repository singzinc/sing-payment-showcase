package com.sing.payment.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

public abstract class BaseDaoImpl<T, ID extends Serializable> extends HibernateDaoSupport implements BaseDao<T, ID> {

	protected Class<T> entityClass;
	protected String className;

	public BaseDaoImpl() {
		entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		className = entityClass.getName();
	}

	public void saveOrUpdate(T t) {
		getHibernateTemplate().saveOrUpdate(t);
	}

	public void save(T t) {
		this.getHibernateTemplate().save(t);

	}

	public void saveList(List<T> ts) {
		for (T t : ts) {
			this.getHibernateTemplate().save(t);
		}

	}

	public void delete(T t) {
		this.getHibernateTemplate().delete(t);

	}

	/** update entity can using on add update and delete
	 * 
	 * @param hql
	 * @param params */
	@SuppressWarnings("unchecked")
	public void update(final String hql, final Object... params) {
		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(hql);
				for (int i = 0; i < params.length; i++) {
					query.setParameter(i, params[i]);
				}
				query.executeUpdate();
				return null;
			}
		});
	}

	public void update(T t) {
		this.getHibernateTemplate().update(t);

	}

	@SuppressWarnings("unchecked")
	public T findById(ID id) {
		return (T) getHibernateTemplate().get(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		List<T> list = (List<T>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(entityClass);
				criteria.setCacheable(true);
				criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
				List<T> list = criteria.list();
				return list;
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll(final String propertyName, final boolean isAsc) {
		List<T> list = (List<T>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(entityClass);
				if (null != propertyName) {
					if (isAsc) {
						criteria.addOrder(Order.asc(propertyName));
					} else {
						criteria.addOrder(Order.desc(propertyName));
					}
				}
				criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
				criteria.setCacheable(true);
				List<T> list = criteria.list();
				return list;
			}
		});
		return list;
	}

	public List<T> findAll(final String propertyName, final boolean isAsc, final Criterion criterions) {
		int firstResult = 0;
		int maxResults = 0;
		return findByCriteria(propertyName, isAsc, firstResult, maxResults, criterions);
	}

	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(final String propertyName, final boolean isAsc, final int firstResult, final int maxResults, final Criterion... criterions) {
		List<T> list = (List<T>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(entityClass);
				for (Criterion criterion : criterions) {
					criteria.add(criterion);
				}
				if (null != propertyName) {
					if (isAsc) {
						criteria.addOrder(Order.asc(propertyName));
					} else {
						criteria.addOrder(Order.desc(propertyName));
					}
				}
				if (maxResults != 0) {
					criteria.setFirstResult(firstResult);
					criteria.setMaxResults(maxResults);
				}
				criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
				List<T> list = criteria.list();
				return list;
			}
		});
		return list;
	}

	public List<T> findByProperty(String propertyName, Object value) {
		Criterion criterion = Restrictions.eq(propertyName, value);
		List<T> list = findAll(null, true, criterion);
		return list;
	}

	public List<T> findByProperty(String propertyName, Object value, final String orderPropertyName, final boolean isAsc) {
		Criterion criterion = Restrictions.eq(propertyName, value);
		int firstResult = 0;
		int maxResults = 0;
		return findByCriteria(orderPropertyName, isAsc, firstResult, maxResults, criterion);
	}

	@SuppressWarnings("unchecked")
	public T findUniqueByProperty(final String propertyName, final Object value) {
		T t = (T) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(entityClass).add(Restrictions.eq(propertyName, value));
				T t = (T) criteria.uniqueResult();
				return t;
			}
		});
		return t;
	}


}
