package com.sing.payment.dao;

import java.util.List;

import org.hibernate.criterion.Criterion;

public interface BaseDao<T, ID> {

	public void saveOrUpdate(T t);

	public void save(T t);

	public void saveList(List<T> ts);

	public void delete(T t);

	public void update(final String hql, final Object... params);

	public void update(T t);

	public T findById(ID id);

	public T findUniqueByProperty(final String propertyName, final Object value);

	public List<T> findAll();

	public List<T> findAll(final String propertyName, final boolean isAsc);

	public List<T> findAll(final String propertyName, final boolean isAsc, final Criterion criterions);

	public List<T> findByProperty(String propertyName, Object value);

	public List<T> findByProperty(String propertyName, Object value, final String orderPropertyName, final boolean isAsc);

}
