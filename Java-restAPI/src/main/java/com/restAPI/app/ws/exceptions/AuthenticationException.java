package com.restAPI.app.ws.exceptions;

public class AuthenticationException extends RuntimeException {

	private static final long serialVersionUID = -4221121641994832624L;

	public AuthenticationException(String message) {
		super(message);
	}

}
