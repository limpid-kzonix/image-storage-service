package com.omnie.controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.omnie.model.mongo.entities.Image;
import com.omnie.model.mongo.entities.ImageSource;
import com.omnie.model.service.ImageStorageService;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
			String fileName = picture.getFilename( );
			String contentType = picture.getContentType( );
			File file = picture.getFile( );

			BufferedImage image = ImageIO.read( file );
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream( );

			ImageIO.write( image, "jpg", byteArrayOutputStream );

			Image imageEntity = new Image( );
			imageEntity.setName( String.format( "[%s]-{%s}", contentType, ( UUID.randomUUID( ).toString( ) + UUID
					.randomUUID( ) ) ) );
			List< ImageSource > imageSourceList = new ArrayList<>( );
			ImageSource imageSource = new ImageSource( );
			imageSource.setExtension( contentType );
			imageSource.setHeight( image.getHeight( ) );
			imageSource.setWidth( image.getWidth( ) );
			imageSource.setImageName( imageEntity.getName( ) );
			imageSource.setType( "original" );
			imageSource.setOrginalImageSource( byteArrayOutputStream.toByteArray( ) );
			imageSourceList.add( imageSource );
			imageEntity.setImageSources( imageSourceList );
			imageStorageService.saveImage( imageEntity );
			return ok( file, fileName + contentType + ".jpeg" );
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

		byte[] imageByteArray = image.getImageSources().get( 0 ).getOrginalImageSource();;
		InputStream in = new ByteArrayInputStream( imageByteArray );
		BufferedImage bImageFromConvert = ImageIO.read( in );
		File file = File.createTempFile( "logo", ".png" );
		ImageIO.write( bImageFromConvert, "png", file );
		return ok( file );
	}
}
