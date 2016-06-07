package com.test.netcracker.dao.impl;


import com.test.netcracker.dao.IBaseDao;
import com.test.netcracker.dao.exceptions.DaoException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

/**
 * Basic operations to any entities classes.
 * Implements Dao interface.
 * @param <T> object
 * @param <PK> primary key of object
 */
public class BaseDao<T, PK extends Serializable> implements IBaseDao<T, PK> {

    @Autowired
    public SessionFactory sessionFactory;

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public PK create(T t) throws DaoException {
        return (PK) getSession().save(t);
    }

    public void update(T t) throws DaoException {
        getSession().update(t);
    }

    public T read(Serializable id) throws DaoException {
        return (T) getSession().get(getPersistentClass(), id);
    }

    public void delete(T t) throws DaoException {
        getSession().delete(t);
    }

    private Class getPersistentClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
