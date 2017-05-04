package com.omnie.controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.omnie.model.service.ImageStorageService;
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

	private ImageStorageService imageStorageService;

	@Inject
	public ImageController( ImageStorageService imageStorageService ) {
		this.imageStorageService = imageStorageService;
	}


	public Result uploadImage( ) throws IOException {

		Http.MultipartFormData< File > body = request( ).body( ).asMultipartFormData( );
		Http.MultipartFormData.FilePart< File > picture = body.getFile( "picture" );
		if ( picture != null ) {

			try {
				return ok( Json.toJson( imageStorageService.prepareAndSave( picture ) ) );
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
		return ok( );
	}

	public Result getImageSource( String objectId, String imageType )
			throws IOException, ExecutionException, InterruptedException {

		return ok( imageStorageService.getTypedImageById( objectId, imageType ) );
	}
}
