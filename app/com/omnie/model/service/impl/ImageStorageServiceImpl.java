package com.omnie.model.service.impl;

import com.omnie.model.mongo.dao.ImageStorageDao;
import com.omnie.model.mongo.entities.Image;
import com.omnie.model.mongo.entities.ImageSource;
import com.omnie.model.service.ImageStorageService;
import com.omnie.model.service.utils.ImageProperty;
import play.mvc.Http;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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

	@Override
	public List< ByteArrayOutputStream > prepocessingImage( Http.MultipartFormData.FilePart< File > picture )
			throws IOException {
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
		saveImage( imageEntity );
		return null;
	}

	@Override public File prepareAndSave( Http.MultipartFormData.FilePart< File > picture )
			throws IOException, ExecutionException, InterruptedException {
		String fileName = picture.getFilename( );
		String contentType = picture.getContentType( );
		File file = picture.getFile( );
		BufferedImage image = ImageIO.read( file );
		int dimension = image.getWidth( ) > image.getHeight
				( ) ? ( int ) ( image.getHeight( ) / 1.5 ) : ( int ) ( image.getWidth( ) / 1.5 );
		image = cropImage( image,
		                   new ImageProperty(
				                   image.getHeight( ) / 4, image.getWidth( ) / 4, dimension, dimension
		                   ) ).get( );
		image = resizeImageWithHint( image ).get( );
		File outputFile = File.createTempFile( "logo", ".jpg" );
		ImageIO.write( image, "jpg", outputFile );
		return outputFile;

	}

	@Override public List< ByteArrayOutputStream > preProcessingImage( Image file ) {
		return null;
	}

	@Override public File getTypedImageById( String objectId, String type ) {
		return null;
	}

	@Override public File getOriginalImage( String objectId ) {
		return null;
	}

	CompletableFuture< BufferedImage > cropImage( BufferedImage bufferedImage, ImageProperty property )
			throws ExecutionException, InterruptedException {
		List< BufferedImage > images = new ArrayList<>( 3 );


		return CompletableFuture.supplyAsync(
				( ) -> bufferedImage.getSubimage( property.getLeft( ), property.getTop( ), property.getWidth
						                                  ( ),
				                                  property.getHeight( )
				                                ) ).thenApply( f -> {
			f.coerceData( true );
			return f;
		} );


	}

	CompletableFuture< BufferedImage > resizeImageWithHint( BufferedImage originalImage ) {
		return CompletableFuture.supplyAsync( ( ) -> {
			int type = originalImage.getType( ) == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType( );
			BufferedImage resizedImage = new BufferedImage( 256, 256, type );
			Graphics2D g = resizedImage.createGraphics( );
			g.drawImage( originalImage, 0, 0, 256, 256, null );
			g.dispose( );
			g.setComposite( AlphaComposite.Src );

			g.setRenderingHint( RenderingHints.KEY_INTERPOLATION,
			                    RenderingHints.VALUE_INTERPOLATION_BILINEAR );
			g.setRenderingHint( RenderingHints.KEY_RENDERING,
			                    RenderingHints.VALUE_RENDER_QUALITY );
			g.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
			                    RenderingHints.VALUE_ANTIALIAS_ON );
			return resizedImage;
		} ).thenApply( f -> {
			f.coerceData( true );
			return f;
		} );

	}

}
