package com.restAPI.app.ws.resource;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.BeanUtils;
import com.restAPI.app.ws.annotations.Secured;
import com.restAPI.app.ws.dto.UserDTO;
import com.restAPI.app.ws.model.request.CreateUserRequestModel;
import com.restAPI.app.ws.model.request.UpdateUserRequestModel;
import com.restAPI.app.ws.model.response.DeleteUserProfileResponseModel;
import com.restAPI.app.ws.model.response.RequestOperation;
import com.restAPI.app.ws.model.response.ResponseStatus;
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

	@Secured
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

	@GET
	@Path("/getListOfUsers")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<UserProfileRest> getUsers(@DefaultValue("0") @QueryParam("start") int start,
			@DefaultValue("10") @QueryParam("end") int end) {

		UsersService userService = new UsersServiceImpl();
		List<UserDTO> users = userService.getUsers(start, end);

		// Prepare return value
		List<UserProfileRest> returnValue = new ArrayList<UserProfileRest>();
		for (UserDTO userDto : users) {
			UserProfileRest userResponseModel = new UserProfileRest();
			BeanUtils.copyProperties(userDto, userResponseModel);
			returnValue.add(userResponseModel);
		}

		return returnValue;
	}

	@Secured
	@PUT
	@Path("/updateUser/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public UserProfileRest updateUserDetails(@PathParam("id") String id, UpdateUserRequestModel userDetails) {

		UsersService userService = new UsersServiceImpl();
		UserDTO storedUserDetails = userService.getUser(id);

		// set values only if they are not null and empty
		if (userDetails.getFirstName() != null && !userDetails.getFirstName().isEmpty()) {
			storedUserDetails.setFirstName(userDetails.getFirstName());
		}
		if (userDetails.getLastName() != null && !userDetails.getLastName().isEmpty()) {
			storedUserDetails.setLastName(userDetails.getLastName());
		}

		// Update User Details
		userService.updateUserDetails(storedUserDetails);

		// return updated user detail
		UserProfileRest returnValue = new UserProfileRest();
		BeanUtils.copyProperties(storedUserDetails, returnValue);

		return returnValue;
	}

	@Secured
	@DELETE
	@Path("/deleteUser/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public DeleteUserProfileResponseModel deleteUserProfile(@PathParam("id") String id) {
		DeleteUserProfileResponseModel returnValue = new DeleteUserProfileResponseModel();
		returnValue.setRequestOperation(RequestOperation.DELETE);

		UsersService userService = new UsersServiceImpl();
		UserDTO storedUserDetails = userService.getUser(id);

		userService.deleteUser(storedUserDetails);

		returnValue.setResponseStatus(ResponseStatus.SUCCESS);

		return returnValue;
	}
}
