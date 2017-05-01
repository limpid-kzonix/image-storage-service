package com.omnie.model.service.impl;

import com.omnie.model.mongo.dao.ImageStorageDao;
import com.omnie.model.mongo.entities.Image;
import com.omnie.model.service.ImageStorageService;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

/**
 * Created by Harmeet Singh(Taara) on 27/12/16.
 */
@Singleton
public class ImageStorageServiceImpl implements ImageStorageService {

	private ImageStorageDao imageStorageDao;

	@Inject
	public ImageStorageServiceImpl( ImageStorageDao imageStorageDao ) {
		this.imageStorageDao = imageStorageDao;
	}


	@Override public void saveImage( Image image ) {
		imageStorageDao.save( image );
	}

	@Override public Image findImageById( String id ) {
		return imageStorageDao.findById( id );
	}

	@Override public void delete( String id ) {
		imageStorageDao.delete( id );
	}

	@Override public List< Image > getAll( ) {
		return null;
	}

	@Override public List< Image > getAll( int start, int limit ) {
		return null;
	}
}
