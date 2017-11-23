package com.omnie;

import com.omnie.model.KunderaEntityManageFactory;
import com.omnie.model.mongo.dao.ImageStorageDao;
import com.omnie.model.mongo.dao.impl.ImageStorageDaoimpl;
import com.omnie.model.service.ImageStorageService;
import com.omnie.model.service.impl.ImageStorageServiceImpl;
import com.google.inject.AbstractModule;

/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.
 * <p>
 * Play will automatically use any class called `com.omnie.Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
public class Module extends AbstractModule {

	@Override
	public void configure( ) {
		bind( ImageStorageService.class ).to( ImageStorageServiceImpl.class );
		bind( ImageStorageDao.class ).to( ImageStorageDaoimpl.class );
		bind( KunderaEntityManageFactory.class ).asEagerSingleton( );
	}

}
