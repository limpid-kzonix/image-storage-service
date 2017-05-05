package com.omnie.model.mongo.dao;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Harmeet Singh(Taara) on 27/12/16.
 */
public interface GenericDao< E > {

	@Transactional
	E save( E entity );

	@Transactional
	void update( E entity );

	@Transactional
	E findById( String objectId );

	@Transactional
	List< E > findAll( );

	@Transactional
	void delete( E entity );

	@Transactional
	void delete( String objectId );

	@Transactional
	void deleteByObjectId( List<String> objectId );

}
