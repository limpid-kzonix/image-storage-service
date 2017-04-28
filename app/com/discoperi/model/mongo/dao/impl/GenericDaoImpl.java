package com.discoperi.model.mongo.dao.impl;

import com.discoperi.model.mongo.dao.GenericDao;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by Harmeet Singh(Taara) on 27/12/16.
 */
public abstract class GenericDaoImpl<T> implements GenericDao<T> {

    private Class<T> entityClass;
    private EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory( "mongoUnit" );
    private EntityManager entityManager;


    public GenericDaoImpl() {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory( "mongoUnit" );
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        entityClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

    protected EntityManager getEntityManager() {
        return this.managerFactory.createEntityManager();
    }

    @Override
    public void save(T t) {
        getEntityManager().persist(t);
    }

    @Override
    public T findById(int id) {
        return getEntityManager().createQuery("SELECT em FROM "+entityClass.getSimpleName()+" em WHERE em.id = :id", entityClass)
                .setParameter("id", id).getSingleResult();
    }

    @Override
    public List<T> findAll() {
        return getEntityManager().createQuery("SELECT em FROM Employee em", entityClass)
                .getResultList();
    }


}
