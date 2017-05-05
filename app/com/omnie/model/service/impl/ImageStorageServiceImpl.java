package com.omnie.model.service.impl;

import com.omnie.model.mongo.dao.ImageStorageDao;
import com.omnie.model.mongo.entities.Image;
import com.omnie.model.mongo.entities.ImageSource;
import com.omnie.model.service.ImageStorageService;
import com.omnie.model.service.utils.ImageExtension;
import com.omnie.model.service.utils.ImageSourceType;
import play.mvc.Http;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Created by Harmeet Singh(Taara) on 27/12/16.
 */
@Singleton
public class ImageStorageServiceImpl implements ImageStorageService {

	private ImageStorageDao imageStorageDao;

	private SecureRandom random = new SecureRandom( );

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

	@Override public void multipleDelete( List<String> ids ) {
		imageStorageDao.deleteByObjectId( ids );
	}


	@Override public Image prepareAndSave( Http.MultipartFormData.FilePart< File > picture )
			throws IOException, ExecutionException, InterruptedException {
		String fileName = picture.getFilename( );
		String contentType = picture.getContentType( );
		File file = picture.getFile( );
		BufferedImage image = ImageIO.read( file );


		image = cropImage( image, getImageDimension( image ).get( ) ).get( );

		Image imageEntity = new Image( );
		imageEntity.setImageId( new BigInteger( 130, random ).toString( 32 ) + UUID.randomUUID( ).toString( ) );
		imageEntity.setName( String.format( "[%s]-{%s}", contentType.replace( '/', '\0' ), ( UUID.randomUUID( )
				.toString( )
				+ UUID
				.randomUUID( ) ) + ".jpg" ) );
		List< ImageSource > imageSourceList = new ArrayList<>( );
		imageSourceList.addAll( prepareImageSources( image ).get( ) );
		imageEntity.setImageSources( imageSourceList );
		saveImage( imageEntity );

		//imageStorageDao.save( imageEntity );
		File outputFile = File.createTempFile( imageEntity.getImageId( ), ".jpg" );
		ImageIO.write( image, "jpg", outputFile );
		return imageEntity;

	}

	@Override public File getTypedImageById( String objectId, String type )
			throws ExecutionException, InterruptedException {
		Image imageEntity = findImageById( objectId );
		CompletableFuture< byte[] > bytePromise = CompletableFuture.supplyAsync( ( ) -> {
			for ( ImageSource imageSource : imageEntity.getImageSources( ) ) {
				if ( imageSource.getType( ).equals( type.toUpperCase( ) ) ) {
					return imageSource.getImageSource( );
				}
			}
			return imageEntity.getImageSources( ).isEmpty( ) ? null : imageEntity.getImageSources( ).get( 0 )
					.getImageSource( );
		} ).thenApply( byteArray -> byteArray );
		CompletableFuture<File> filePromise = CompletableFuture.supplyAsync( () -> {
			File file = null;
			try {
				file = File.createTempFile( UUID.randomUUID().toString(),"." + ImageExtension.JPG.getType() );
				InputStream in = new ByteArrayInputStream( bytePromise.get() );
				BufferedImage imageFromSource = ImageIO.read( in );

				ImageIO.write( imageFromSource, ImageExtension.JPG.getType(), file );
				return file;
			} catch ( IOException ignored ) {

			} catch ( InterruptedException | ExecutionException e ) {
				e.printStackTrace( );
			}
			return file;
		} ).thenApply( file -> {
			if ( file == null ){
				return File.listRoots()[1];
			}
			return file;
		} );
		return filePromise.get();

	}

	@Override public File getOriginalImage( String objectId ) {
		return null;
	}

	private CompletableFuture< BufferedImage > cropImage( BufferedImage bufferedImage, Rectangle2D property )
			throws ExecutionException, InterruptedException {

		return CompletableFuture.supplyAsync(
				( ) -> bufferedImage
						.getSubimage( ( int ) property.getX( ), ( int ) property.getY( ), ( int ) property.getWidth( ),
						              ( int ) property.getHeight( )
						            )
		                                    ).thenApply( f -> {
			f.coerceData( true );
			return f;
		} );


	}


	private CompletableFuture< BufferedImage > resizeImageWithHint( BufferedImage originalImage, int width,
	                                                                int height ) {
		return CompletableFuture.supplyAsync( ( ) -> {
			int type = originalImage.getType( ) == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType( );
			BufferedImage resizedImage = new BufferedImage( width, height, type );
			Graphics2D g = resizedImage.createGraphics( );
			g.drawImage( originalImage, 0, 0, width, height, null );
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

	private CompletableFuture< Rectangle2D > getImageDimension( BufferedImage image ) {
		return CompletableFuture.supplyAsync( ( ) -> {
			int width = image.getWidth( );
			int height = image.getHeight( );

			int hCenter = ( int ) width / 2;
			int vCenter = ( int ) height / 2;

			int dimension = image.getWidth( ) > image.getHeight
					( ) ? image.getHeight( ) : image.getWidth( );

			return new Rectangle( hCenter - dimension / 2, vCenter - dimension / 2, dimension, dimension );


		} ).thenApply( Rectangle::getBounds2D );
	}

	private CompletableFuture< byte[] > generateImageSource( BufferedImage image ) {
		return CompletableFuture.supplyAsync( ( ) -> {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream( );

			try {
				ImageIO.write( image, "jpg", byteArrayOutputStream );
			} catch ( IOException e ) {
				e.printStackTrace( );
			}
			return byteArrayOutputStream;
		} ).thenApply( ByteArrayOutputStream::toByteArray );
	}

	CompletableFuture< List< ImageSource > > prepareImageSources( BufferedImage image ) {

		return CompletableFuture.supplyAsync( ( ) -> {
			List< ImageSource > sources = new ArrayList< ImageSource >( );
			try {
				sources.add( prepareSmallImageSources( image ).get( ) );
				sources.add( prepareMediumImageSources( image ).get( ) );
				sources.add( prepareOriginalImageSources( image ).get( ) );
			} catch ( InterruptedException | ExecutionException e ) {
				e.printStackTrace( );
			}

			return sources;
		} ).thenApply( a -> a.stream( ).distinct( ).collect( Collectors.toList( ) ) );

	}

	private CompletableFuture< ImageSource > prepareSmallImageSources( BufferedImage image ) {

		return CompletableFuture.supplyAsync( ( ) -> {

			ImageSource imageSource = new ImageSource( );
			imageSource.setImageId( new BigInteger( 130, random ).toString( 32 ) + UUID.randomUUID( ).toString( ) );
			imageSource.setExtension( ImageExtension.JPG.getType( ) );
			imageSource.setHeight( image.getHeight( ) );
			imageSource.setWidth( image.getWidth( ) );
			imageSource.setType( ImageSourceType.SMALL.getType( ) );
			try {
				imageSource
						.setImageSource( generateImageSource( resizeImageWithHint( image, 200, 200 ).get( ) ).get( ) );
			} catch ( InterruptedException | ExecutionException e ) {
				e.printStackTrace( );
			}
			return imageSource;
		} ).thenApply( a -> a );
	}

	private CompletableFuture< ImageSource > prepareMediumImageSources( BufferedImage image ) {

		return CompletableFuture.supplyAsync( ( ) -> {

			ImageSource imageSource = new ImageSource( );
			imageSource.setImageId( new BigInteger( 130, random ).toString( 32 ) + UUID.randomUUID( ).toString( ) );
			imageSource.setExtension( ImageExtension.JPG.getType( ) );
			imageSource.setHeight( image.getHeight( ) );
			imageSource.setWidth( image.getWidth( ) );
			imageSource.setType( ImageSourceType.MEDIUM.getType( ) );
			try {
				imageSource
						.setImageSource( generateImageSource( resizeImageWithHint( image, 500, 500 ).get( ) ).get( ) );
			} catch ( InterruptedException | ExecutionException e ) {
				e.printStackTrace( );
			}
			return imageSource;
		} ).thenApply( a -> a );

	}

	private CompletableFuture< ImageSource > prepareOriginalImageSources( BufferedImage image ) {

		return CompletableFuture.supplyAsync( ( ) -> {

			ImageSource imageSource = new ImageSource( );
			imageSource.setImageId( new BigInteger( 130, random ).toString( 32 ) + UUID.randomUUID( ).toString( ) );
			imageSource.setExtension( ImageExtension.JPG.getType( ) );
			imageSource.setHeight( image.getHeight( ) );
			imageSource.setWidth( image.getWidth( ) );
			imageSource.setType( ImageSourceType.ORIGINAL.getType( ) );
			try {
				imageSource.setImageSource( generateImageSource( image ).get( ) );
			} catch ( InterruptedException | ExecutionException e ) {
				e.printStackTrace( );
			}
			return imageSource;
		} ).thenApply( a -> a );

	}

}
