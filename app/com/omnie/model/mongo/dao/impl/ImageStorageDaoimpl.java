package com.omnie.model.mongo.dao.impl;

import com.omnie.model.KunderaEntityManageFactory;
import com.omnie.model.mongo.dao.ImageStorageDao;
import com.omnie.model.mongo.entities.Image;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Harmeet Singh(Taara) on 27/12/16.
 */
@Singleton
public class ImageStorageDaoimpl extends GenericDaoImpl< Image > implements ImageStorageDao {

	@Inject
	public ImageStorageDaoimpl( KunderaEntityManageFactory entityManageFactory ) {
		super( entityManageFactory );
	}

	@Override public void update( Image entity ) {

	}
}
