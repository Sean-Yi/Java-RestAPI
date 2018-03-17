package com.restAPI.app.ws.service;

import java.util.List;

import com.restAPI.app.ws.dto.UserDTO;

public interface UsersService {

	UserDTO createUser(UserDTO user);

	UserDTO getUser(String id);

	UserDTO getUserByUserName(String userName);

	List<UserDTO> getUsers(int start, int limit);

	void updateUserDetails(UserDTO userDetails);

	void deleteUser(UserDTO userDto);
}
