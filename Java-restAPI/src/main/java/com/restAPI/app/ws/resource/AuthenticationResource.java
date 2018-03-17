package com.restAPI.app.ws.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.restAPI.app.ws.dto.UserDTO;
import com.restAPI.app.ws.model.request.Logininfo;
import com.restAPI.app.ws.model.response.AuthenticationToken;
import com.restAPI.app.ws.service.AuthenticationService;
import com.restAPI.app.ws.service.impl.AuthenticationServiceImpl;

@Path("/authentication-management")
public class AuthenticationResource {

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public AuthenticationToken userLogin(Logininfo logininfo) {
		AuthenticationToken returnValue = new AuthenticationToken();

		AuthenticationService authenticationService = new AuthenticationServiceImpl();
		UserDTO authenticatedUser = authenticationService.authenticate(logininfo.getUserName(),
				logininfo.getUserPassword());

		// Reset Access Token
		authenticationService.resetEncryptedPassword(logininfo.getUserPassword(), authenticatedUser);

		// Generate access token
		String accessToken = authenticationService.issueAccessToken(authenticatedUser);

		returnValue.setId(authenticatedUser.getUserId());
		returnValue.setToken(accessToken);

		return returnValue;
	}
}