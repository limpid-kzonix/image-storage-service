package com.omnie.controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.omnie.model.mongo.entities.Image;
import com.omnie.model.service.ImageStorageService;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
				return ok( imageStorageService.prepareAndSave( picture ) );
			} catch ( ExecutionException e ) {
				e.printStackTrace( );
			} catch ( InterruptedException e ) {
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

	public Result getImageSource( String objectId ) throws IOException {
		Image image = imageStorageService.findImageById( objectId );

		byte[] imageByteArray = image.getImageSources( ).get( 0 ).getOrginalImageSource( );
		;
		InputStream in = new ByteArrayInputStream( imageByteArray );
		BufferedImage bImageFromConvert = ImageIO.read( in );
		File file = File.createTempFile( "logo", ".png" );
		ImageIO.write( bImageFromConvert, "png", file );
		return ok( file );
	}
}
