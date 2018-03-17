package com.restAPI.app.ws.model.response;

public enum ErrorMessages {
	
    MISSING_REQUIRED_FIELD("Missing required field. Please fill required field"),
    Data_ALREADY_EXISTS("Data already exists"),
    INTERNAL_SERVER_ERROR("Internal server error"),
    NO_DATA_FOUND("Data is not found"),
    AUTHENTICATION_FAILED("Authentication failed"),
    COULD_NOT_UPDATE_DATA("Could not update data"),
    COULD_NOT_DELETE_DATA("Could not delete data");
	
	private String errorMessage;

	ErrorMessages(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
