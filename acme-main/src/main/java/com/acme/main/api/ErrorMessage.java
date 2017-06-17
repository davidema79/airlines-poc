package com.acme.main.api;

/**
 * POJO for Json representation on error.
 * 
 * @author Davide Martorana
 *
 */
public class ErrorMessage {

	private String message;
	
	public ErrorMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
