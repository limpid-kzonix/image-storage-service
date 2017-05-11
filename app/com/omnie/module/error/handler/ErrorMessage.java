package com.omnie.module.error.handler;


import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * Created by limpid on 5/11/17.
 */
@Data
@AllArgsConstructor
public class ErrorMessage {
	private Integer code = 404;
	private String message;

	public ErrorMessage(String message){
		this.message = message;
	}

}
