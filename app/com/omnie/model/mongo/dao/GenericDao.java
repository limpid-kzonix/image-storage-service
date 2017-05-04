package com.omnie.model.mongo.dao;

import java.util.List;

/**
 * Created by Harmeet Singh(Taara) on 27/12/16.
 */
public interface GenericDao< E > {

	E save( E entity );

	void update( E entity );

	E findById( String objectId );

	List< E > findAll( );

	void delete( E entity );

	void delete( String objectId );

}
