package com.omnie.model.service;

import com.omnie.model.mongo.entities.Image;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Harmeet Singh(Taara) on 27/12/16.
 */
public interface ImageStorageService {

	@Transactional
	void saveImage( Image image );

	@Transactional
	Image findImageById( String id );

	@Transactional
	void delete( String id );

	@Transactional
	List<Image> getAll();

	@Transactional
	List<Image> getAll(int start, int limit);
}
