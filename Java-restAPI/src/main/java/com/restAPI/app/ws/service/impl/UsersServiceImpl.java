package com.restAPI.app.ws.service.impl;

import java.util.List;
import com.restAPI.app.ws.Utils.UserProfileUtils;
import com.restAPI.app.ws.dao.UserDAO;
import com.restAPI.app.ws.dao.impl.UserDaoImpl;
import com.restAPI.app.ws.dto.UserDTO;
import com.restAPI.app.ws.exceptions.CouldNotCreateDataException;
import com.restAPI.app.ws.exceptions.CouldNotDeleteDataException;
import com.restAPI.app.ws.exceptions.CouldNotUpdateDataException;
import com.restAPI.app.ws.exceptions.NoDataFoundException;
import com.restAPI.app.ws.model.response.ErrorMessages;
import com.restAPI.app.ws.service.UsersService;

public class UsersServiceImpl implements UsersService {

	UserDAO database;
	UserProfileUtils userProfileUtils;

	public UsersServiceImpl() {
		this.database = new UserDaoImpl();
		this.userProfileUtils = new UserProfileUtils();
	}
	public UserDTO createUser(UserDTO user) {
		UserDTO returnValue = null;

		// Validate the required fields
		userProfileUtils.validateRequiredFields(user);

		// Check if user already exists
		UserDTO existingUser = this.getUserByUserName(user.getEmail());
		if (existingUser != null) {
			throw new CouldNotCreateDataException(ErrorMessages.Data_ALREADY_EXISTS.name());
		}

		// Generate user id
		String userId = userProfileUtils.generateUserId(30);
		user.setUserId(userId);

		// Generate salt
		String salt = userProfileUtils.getSalt(30);
		user.setSalt(salt);

		// Generate encrypted password
		String encryptedPassword = userProfileUtils.generateSecurePassword(user.getPassword(), salt);
		user.setEncryptedPassword(encryptedPassword);

		try {
			this.database.openConnection();
			returnValue = this.database.saveUser(user);
		} catch (Exception ex) {
			throw new CouldNotCreateDataException(ex.getMessage());
		} finally {
			this.database.closeConnection();
		}
		return returnValue;
	}
	public UserDTO getUser(String id) {
		UserDTO returnValue = null;
		try {
			this.database.openConnection();
			returnValue = this.database.getUser(id);
		} catch (Exception ex) {
			throw new NoDataFoundException(ex.getMessage());
		} finally {
			this.database.closeConnection();
		}
		return returnValue;
	}

	public UserDTO getUserByUserName(String userName) {
		UserDTO userDto = null;

		if (userName == null || userName.isEmpty()) {
			return userDto;
		}

		try {
			this.database.openConnection();
			userDto = this.database.getUserByUserName(userName);
		} finally {
			this.database.closeConnection();
		}

		return userDto;
	}

	public List<UserDTO> getUsers(int start, int limit) {
		List<UserDTO> users = null;
		try {
			this.database.openConnection();
			users = this.database.getUsers(start, limit);
		} finally {
			this.database.closeConnection();
		}

		return users;
	}

	public void updateUserDetails(UserDTO userDetails) {
		try {
			this.database.openConnection();
			this.database.updateUser(userDetails);
		} catch (Exception ex) {
			throw new CouldNotUpdateDataException(ex.getMessage());
		} finally {
			this.database.closeConnection();
		}
	}

	public void deleteUser(UserDTO userDto) {
		try {
			this.database.openConnection();
			this.database.deleteUser(userDto);
		} catch (Exception ex) {
			throw new CouldNotDeleteDataException(ex.getMessage());
		} finally {
			this.database.closeConnection();
		}
	}

}
