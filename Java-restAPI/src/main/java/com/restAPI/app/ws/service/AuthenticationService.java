package com.restAPI.app.ws.service;

import com.restAPI.app.ws.dto.UserDTO;
import com.restAPI.app.ws.exceptions.AuthenticationException;

public interface AuthenticationService {
	UserDTO authenticate(String userName, String password) throws AuthenticationException;

	String issueAccessToken(UserDTO userProfile) throws AuthenticationException;

	void resetEncryptedPassword(String password, UserDTO userProfile);
}
