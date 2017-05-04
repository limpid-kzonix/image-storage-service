package com.omnie.model.service.utils;

/**
 * Created by limpid on 5/1/17.
 */

public enum ImageSourceType {
	SMALL("SMALL"),
	MEDIUM("MEDIUM"),
	ORIGINAL("ORIGINAL");

	private String type;

	ImageSourceType(String type){
		this.type = type;
	}


	public String getType( ) {
		return type;
	}
}
