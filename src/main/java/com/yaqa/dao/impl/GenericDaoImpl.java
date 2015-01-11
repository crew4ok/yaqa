package com.yaqa.dao.impl;

import com.yaqa.dao.GenericDao;
import com.yaqa.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class GenericDaoImpl<T> implements GenericDao<T> {

    @PersistenceContext
    protected EntityManager em;

    protected Class<T> entityClass;

    protected GenericDaoImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public List<T> getAll() {
        return em.createQuery("select e " +
                        " from " + entityClass.getSimpleName() + " e",
                entityClass)
                .getResultList();
    }

    @Override
    public T getById(Long id) {
        final T entity = em.find(entityClass, id);
        if (entity == null) {
            throw new NotFoundException(entityClass, id);
        }
        return entity;
    }

    @Override
    public void save(T entity) {
        em.persist(entity);
    }

    @Override
    public void remove(T entity) {
        em.remove(entity);
    }

    @Override
    public void refresh(T entity) {
        em.refresh(entity);
    }

    @Override
    public T merge(T entity) {
        return em.merge(entity);
    }

}
