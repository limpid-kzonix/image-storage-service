package com.omnie.controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.omnie.model.service.ImageStorageService;
import play.cache.CacheApi;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by Harmeet Singh(Taara) on 27/12/16.
 */
@Singleton
public class ImageController extends Controller {

	private CacheApi cache;


	private ImageStorageService imageStorageService;

	@Inject
	public ImageController( ImageStorageService imageStorageService,  CacheApi cache ) {
		this.imageStorageService = imageStorageService;
		this.cache = cache;
	}


	public Result uploadImage( ) throws IOException {

		Http.MultipartFormData< File > body = request( ).body( ).asMultipartFormData( );
		Http.MultipartFormData.FilePart< File > picture = body.getFile( "picture" );
		if ( picture != null ) {

			try {
				return ok( Json.toJson( imageStorageService.prepareAndSave( picture ).getImageId( ) ) );
			} catch ( ExecutionException | InterruptedException e ) {
				e.printStackTrace( );
			}
			return ok( "jpeg" );
		} else {
			flash( "error", "Missing file" );
			return badRequest( "Error" );
		}
	}

	public Result deleteImage( String objectId ) {
		imageStorageService.delete( objectId );
		return ok( objectId );
	}


	public Result getImageSource( String objectId, String imageType )
			throws IOException, ExecutionException, InterruptedException {
		String _objectId = objectId;
		String _type = imageType;
		File image = cache.getOrElse( objectId+_type, () -> imageStorageService.getTypedImageById( _objectId, _type ) );
		cache.set( objectId+_type, image );
		return ok( image );
	}
}
