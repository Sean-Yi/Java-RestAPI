package com.restAPI.app.ws.exceptions;

public class NoDataFoundException extends RuntimeException {

	private static final long serialVersionUID = -535084087366976393L;

	public NoDataFoundException(String message) {
		super(message);
	}

}
