package com.restAPI.app.ws.filters;

import java.io.IOException;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

import com.restAPI.app.ws.Utils.UserProfileUtils;
import com.restAPI.app.ws.annotations.Secured;
import com.restAPI.app.ws.dto.UserDTO;
import com.restAPI.app.ws.exceptions.AuthenticationException;
import com.restAPI.app.ws.service.UsersService;
import com.restAPI.app.ws.service.impl.UsersServiceImpl;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

	public void filter(ContainerRequestContext requestContext) throws IOException {
		// get Authorization info from HTTP header and check
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
			throw new AuthenticationException("Authorization header must be provided");
		}

		// get accessToken from header
		String token = authorizationHeader.substring("Bearer".length()).trim();

		// get user id from url parameter
		String userId = requestContext.getUriInfo().getPathParameters().getFirst("id");

		// check validation of access token
		validateToken(token, userId);

	}

	private void validateToken(String token, String userId) throws AuthenticationException {

		// Get user profile from database
		UsersService usersService = new UsersServiceImpl();
		UserDTO userProfile = usersService.getUser(userId);

		// merge accessToken, one from DB and one from HTTP header.
		String HttpheaderToken = userProfile.getToken() + token;

		// to validate given token, generate new token base on selected user profile
		String securePassword = userProfile.getEncryptedPassword();
		String accessTokenMaterial = userId + userProfile.getSalt();
		String newGeneratedToken = new UserProfileUtils().generateAccessToken(securePassword, accessTokenMaterial);

		// Compare two access tokens, one from HTTP header and one from newly generated
		// accessToken
		if (!newGeneratedToken.equalsIgnoreCase(HttpheaderToken)) {
			throw new AuthenticationException("Authorization token did not match");
		}
	}

}
