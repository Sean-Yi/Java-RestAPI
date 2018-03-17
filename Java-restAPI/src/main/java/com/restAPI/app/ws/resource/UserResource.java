package com.restAPI.app.ws.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.BeanUtils;

import com.restAPI.app.ws.dto.UserDTO;
import com.restAPI.app.ws.model.request.CreateUserRequestModel;
import com.restAPI.app.ws.model.response.UserProfileRest;
import com.restAPI.app.ws.service.UsersService;
import com.restAPI.app.ws.service.impl.UsersServiceImpl;

@Path("/user-management")
public class UserResource {

	@POST
	@Path("/create-user")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public UserProfileRest createUser(CreateUserRequestModel requestObject) {
		UserProfileRest returnValue = new UserProfileRest();

		// Prepare UserDTO
		UserDTO userDto = new UserDTO();
		BeanUtils.copyProperties(requestObject, userDto);

		// Create new user
		UsersService userService = new UsersServiceImpl();
		UserDTO createdUserProfile = userService.createUser(userDto);

		// Prepare response
		BeanUtils.copyProperties(createdUserProfile, returnValue);

		return returnValue;
	}

	@GET
	@Path("/getUser/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public UserProfileRest getUserProfile(@PathParam("id") String id) {

		UsersService userService = new UsersServiceImpl();
		UserDTO userProfile = userService.getUser(id);

		UserProfileRest returnValue = new UserProfileRest();
		BeanUtils.copyProperties(userProfile, returnValue);

		return returnValue;
	}
}
