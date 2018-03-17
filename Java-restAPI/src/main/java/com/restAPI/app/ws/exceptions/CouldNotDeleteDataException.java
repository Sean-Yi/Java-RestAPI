package com.restAPI.app.ws.exceptions;

public class CouldNotDeleteDataException extends RuntimeException {

	private static final long serialVersionUID = 8830895651159381067L;

	public CouldNotDeleteDataException(String message) {
		super(message);
	}
}
