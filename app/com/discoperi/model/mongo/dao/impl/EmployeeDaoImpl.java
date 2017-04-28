package com.discoperi.model.mongo.dao.impl;

import com.discoperi.model.mongo.dao.EmployeeDao;
import com.discoperi.model.mongo.entities.Employee;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Harmeet Singh(Taara) on 27/12/16.
 */
@Singleton
public class EmployeeDaoImpl extends GenericDaoImpl< Employee > implements EmployeeDao {

	@Inject
	public EmployeeDaoImpl( ) {
		super( );
	}
}
