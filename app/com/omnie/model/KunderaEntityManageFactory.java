package com.omnie.model;

import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import lombok.Getter;
import play.Configuration;


import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by limpid on 4/28/17.
 */

@Singleton
public class KunderaEntityManageFactory {

	@Getter
	private static EntityManagerFactory managerFactory ;//= Persistence.createEntityManagerFactory( "mongoUnit" );
	@Inject
	public KunderaEntityManageFactory(Configuration configuration){
		managerFactory = Persistence.createEntityManagerFactory(configuration.getString("jpa.default") );
	}
}
