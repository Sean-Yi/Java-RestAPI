package com.restAPI.app.ws.exceptions;

public class MissingRequiredFieldException extends RuntimeException {

	private static final long serialVersionUID = 6944438651948293864L;

	public MissingRequiredFieldException(String message) {
		super(message);
	}

}
