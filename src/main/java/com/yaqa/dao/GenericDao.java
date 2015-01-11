package com.yaqa.dao;

import java.util.List;

public interface GenericDao<T> {

    List<T> getAll();

    T getById(Long id);

    void save(T entity);

    void remove(T entity);

    void refresh(T entity);

    T merge(T entity);
}
