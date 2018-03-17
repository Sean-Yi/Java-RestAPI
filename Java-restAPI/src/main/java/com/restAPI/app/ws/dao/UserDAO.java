package com.restAPI.app.ws.dao;

import java.util.List;

import com.restAPI.app.ws.dto.UserDTO;

public interface UserDAO {

	void openConnection();

	UserDTO getUserByUserName(String userName);

	UserDTO saveUser(UserDTO user);

	UserDTO getUser(String id);

	List<UserDTO> getUsers(int start, int limit);

	void updateUser(UserDTO userProfile);

	void deleteUser(UserDTO userProfile);

	void closeConnection();
}

