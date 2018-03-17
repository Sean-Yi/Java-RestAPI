package com.restAPI.app.ws.exceptions;

public class CouldNotCreateDataException extends RuntimeException {

	private static final long serialVersionUID = 7673521716126854084L;

	public CouldNotCreateDataException(String message) {
		super(message);
	}
}
