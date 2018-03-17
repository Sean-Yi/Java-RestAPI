package com.restAPI.app.ws.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import com.restAPI.app.ws.model.response.ErrorMessage;
import com.restAPI.app.ws.model.response.ErrorMessages;

public class CouldNotDeleteDataExceptionMapper implements ExceptionMapper<CouldNotDeleteDataException> {

	public Response toResponse(CouldNotDeleteDataException exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), ErrorMessages.COULD_NOT_DELETE_DATA.name(),
				"https://github.com/Sean-Yi");

		return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
	}
}
