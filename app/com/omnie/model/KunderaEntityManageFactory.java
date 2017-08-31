package com.omnie.model;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import play.Configuration;
import play.Environment;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by limpid on 4/28/17.
 */
@Singleton
public class KunderaEntityManageFactory {

	Configuration configuration;
	@Inject
	public KunderaEntityManageFactory(Configuration configuration){
		this.configuration = configuration;
	}

	@Getter
	private static EntityManagerFactory managerFactory ;//= Persistence.createEntityManagerFactory( "mongoUnit" );

	public KunderaEntityManageFactory(){
		managerFactory = Persistence.createEntityManagerFactory( configuration.getString("jpa.default"));
	}
}
