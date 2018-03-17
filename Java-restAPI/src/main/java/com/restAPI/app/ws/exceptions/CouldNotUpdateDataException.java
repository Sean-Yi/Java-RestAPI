package com.restAPI.app.ws.exceptions;

public class CouldNotUpdateDataException extends RuntimeException {

	private static final long serialVersionUID = 5667596769347260926L;

	public CouldNotUpdateDataException(String message) {
		super(message);
	}

}
