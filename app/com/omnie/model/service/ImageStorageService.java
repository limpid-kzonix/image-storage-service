package com.omnie.model.service;

import com.omnie.model.mongo.entities.Image;
import play.mvc.Http;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Harmeet Singh(Taara) on 27/12/16.
 */
public interface ImageStorageService {


	void saveImage( Image image );


	Image findImageById( String id );


	void delete( String id );

	void multipleDelete( List<String> ids );

	Image prepareAndSave( Http.MultipartFormData.FilePart< File > picture)
			throws IOException, ExecutionException, InterruptedException;

	File getTypedImageById(String objectId, String type) throws ExecutionException, InterruptedException;

	File getOriginalImage(String objectId);
}
