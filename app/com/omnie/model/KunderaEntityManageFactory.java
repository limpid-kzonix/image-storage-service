package com.omnie.model;

import com.google.inject.Singleton;
import lombok.Getter;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by limpid on 4/28/17.
 */
@Singleton
public class KunderaEntityManageFactory {

	@Getter
	private static EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory( "mongoUnit" );

	public KunderaEntityManageFactory(){
		managerFactory = Persistence.createEntityManagerFactory( "mongoUnit" );
	}
}
