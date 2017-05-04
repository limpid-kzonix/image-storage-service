package com.omnie.model.service.utils;

/**
 * Created by limpid on 5/3/17.
 */
public enum ImageExtension {
	JPG(".jpg"),
	PNG(".png"),
	GIF(".gif");

	private String type;

	ImageExtension(String type){
		this.type = type;
	}


	public String getType( ) {
		return type;
	}

	@Override public String toString( ) {
		return type;
	}
}
