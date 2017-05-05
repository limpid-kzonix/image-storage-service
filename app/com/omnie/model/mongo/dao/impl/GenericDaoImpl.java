package com.omnie.model.mongo.dao.impl;

import com.google.inject.Inject;
import com.impetus.client.mongodb.MongoDBClientProperties;
import com.mongodb.WriteConcern;
import com.omnie.model.KunderaEntityManageFactory;
import com.omnie.model.mongo.dao.GenericDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by Harmeet Singh(Taara) on 27/12/16.
 */
public abstract class GenericDaoImpl< E > implements GenericDao< E > {

	private Class< E > entityClass;

	private KunderaEntityManageFactory entityManageFactory;

	@Inject
	public GenericDaoImpl( KunderaEntityManageFactory entityManageFactory ) {
		EntityManagerFactory managerFactory = entityManageFactory.getManagerFactory( );
		ParameterizedType parameterizedType = ( ParameterizedType ) getClass( ).getGenericSuperclass( );
		entityClass = ( Class< E > ) parameterizedType.getActualTypeArguments( )[ 0 ];
	}

	private EntityManager getEntityManager( ) {
		return this.entityManageFactory.getManagerFactory( ).createEntityManager( );
	}

	@Override
	public E save( E entity ) {
		getEntityManager( ).persist( entity );
		return entity;
	}


	@Override
	public E findById( String objectId ) {
		return getEntityManager( )
				.createQuery(
						"SELECT entity FROM " + entityClass.getSimpleName( ) + " entity WHERE entity.imageId = :id",
						entityClass )
				.setParameter( "id", objectId ).getSingleResult( );
	}

	@Override
	public List< E > findAll( ) {
		return getEntityManager( )
				.createQuery( "SELECT entity FROM " + entityClass.getSimpleName( ) + " entity", entityClass )
				.getResultList( );
	}

	@Override
	public void delete( E entity ) {
		getEntityManager( ).remove( entity );
	}

	@Override
	public void delete( String objectId ) {
		getEntityManager( )
				.createQuery( "DELETE FROM " + entityClass.getSimpleName( ) + " entity WHERE entity.imageId = :id" )
				.setParameter( "id", objectId ).executeUpdate( );
	}


	@Override public void deleteByObjectId( List< String > objectIds ) {
		EntityManager em = getEntityManager( );
		em.setProperty( MongoDBClientProperties.ORDERED_BULK_OPERATION, true );
		em.setProperty( MongoDBClientProperties.WRITE_CONCERN, WriteConcern.UNACKNOWLEDGED );
		em.createQuery( "DELETE FROM " + entityClass.getSimpleName( ) + " entity WHERE" +
				                " " +
				                "entity.imageId = :id" )
				.setParameter( "id", objectIds ).executeUpdate( );
		em.close( );
	}


}
