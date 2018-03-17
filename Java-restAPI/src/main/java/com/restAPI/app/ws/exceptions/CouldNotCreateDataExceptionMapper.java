package com.restAPI.app.ws.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import com.restAPI.app.ws.model.response.ErrorMessage;
import com.restAPI.app.ws.model.response.ErrorMessages;

@Provider
public class CouldNotCreateDataExceptionMapper implements ExceptionMapper<CouldNotCreateDataException> {

	public Response toResponse(CouldNotCreateDataException exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), ErrorMessages.Data_ALREADY_EXISTS.name(),
				"https://github.com/Sean-Yi");

		return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
	}
}
