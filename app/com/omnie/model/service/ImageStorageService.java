package com.omnie.model.service;

import com.omnie.model.mongo.entities.Image;
import play.mvc.Http;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
	void multipleDelete( List<String> ids );

	@Transactional
	Image prepareAndSave( Http.MultipartFormData.FilePart< File > picture)
			throws IOException, ExecutionException, InterruptedException;

	File getTypedImageById(String objectId, String type) throws ExecutionException, InterruptedException;

	File getOriginalImage(String objectId);
}
