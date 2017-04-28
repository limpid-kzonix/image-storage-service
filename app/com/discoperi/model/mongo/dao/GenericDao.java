package com.discoperi.model.mongo.dao;

import java.util.List;

/**
 * Created by Harmeet Singh(Taara) on 27/12/16.
 */
public interface GenericDao<T> {

    void save(T t);

    T findById(int id);

    List<T> findAll();

}
