package com.restAPI.app.ws.service.impl;

import com.restAPI.app.ws.Utils.UserProfileUtils;
import com.restAPI.app.ws.dto.UserDTO;
import com.restAPI.app.ws.exceptions.AuthenticationException;
import com.restAPI.app.ws.model.response.ErrorMessages;
import com.restAPI.app.ws.service.AuthenticationService;
import com.restAPI.app.ws.service.UsersService;

public class AuthenticationServiceImpl implements AuthenticationService {

	public UserDTO authenticate(String userName, String password) throws AuthenticationException {
		UsersService usersService = new UsersServiceImpl();
		UserDTO matchedUser = usersService.getUserByUserName(userName);

		if (matchedUser == null || password == null) {
			throw new AuthenticationException(ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage());
		}

		// generate encrypted password with access user password
		String accessUserEncryptedPassword = new UserProfileUtils().generateSecurePassword(password,
				matchedUser.getSalt());

		// compare matched user password and access user password
		boolean authenticated = false;
		if (accessUserEncryptedPassword.equalsIgnoreCase(matchedUser.getEncryptedPassword())) {

			if (userName != null && userName.equalsIgnoreCase(matchedUser.getEmail())) {
				authenticated = true;
			}
		}

		if (!authenticated) {
			throw new AuthenticationException(ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage());
		}

		return matchedUser;

	}

	public String issueAccessToken(UserDTO userProfile) throws AuthenticationException {
		String returnValue = null;

		String accessTokenMaterial = userProfile.getUserId() + userProfile.getSalt();

		String encryptedAccessTokenIn64bit = new UserProfileUtils()
				.generateAccessToken(userProfile.getEncryptedPassword(), accessTokenMaterial);

		// Split encryptedAccessTokenIn64bit into 32bit
		int tokenLength = encryptedAccessTokenIn64bit.length();

		String tokenToSaveToDatabase = encryptedAccessTokenIn64bit.substring(0, tokenLength / 2);
		returnValue = encryptedAccessTokenIn64bit.substring(tokenLength / 2, tokenLength);

		userProfile.setToken(tokenToSaveToDatabase);

		UsersService usersService = new UsersServiceImpl();
		usersService.updateUserDetails(userProfile);
		return returnValue;
	}

	public void resetEncryptedPassword(String password, UserDTO userProfile) {
		// Generate a new salt
		UserProfileUtils userUtils = new UserProfileUtils();
		String salt = userUtils.getSalt(30);

		// Generate a new password
		String securePassword = userUtils.generateSecurePassword(password, salt);
		userProfile.setSalt(salt);
		userProfile.setEncryptedPassword(securePassword);

		// Update user profile
		UsersService usersService = new UsersServiceImpl();
		usersService.updateUserDetails(userProfile);

	}

}
