package com.restAPI.app.ws.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.restAPI.app.ws.model.response.ErrorMessage;
import com.restAPI.app.ws.model.response.ErrorMessages;

@Provider
public class AuthenticationExceptionMapper implements ExceptionMapper<AuthenticationException> {

	public Response toResponse(AuthenticationException exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), ErrorMessages.AUTHENTICATION_FAILED.name(),
				"https://github.com/Sean-Yi");

		return Response.status(Response.Status.UNAUTHORIZED).entity(errorMessage).build();
	}

}