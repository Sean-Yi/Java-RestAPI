package com.restAPI.app.ws.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import com.restAPI.app.ws.model.response.ErrorMessage;
import com.restAPI.app.ws.model.response.ErrorMessages;

@Provider
public class NoDataFoundExceptionMapper implements ExceptionMapper<NoDataFoundException> {

	public Response toResponse(NoDataFoundException exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), ErrorMessages.NO_DATA_FOUND.name(),
				"https://github.com/Sean-Yi");

		return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
	}

}
