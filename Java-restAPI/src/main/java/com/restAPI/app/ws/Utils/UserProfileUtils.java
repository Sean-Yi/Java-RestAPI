package com.restAPI.app.ws.Utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import com.restAPI.app.ws.dto.UserDTO;
import com.restAPI.app.ws.exceptions.MissingRequiredFieldException;
import com.restAPI.app.ws.model.response.ErrorMessages;

public class UserProfileUtils {
	private final Random RANDOM = new SecureRandom();
	private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private final int ITERATIONS = 10000;
	private final int KEY_LENGTH = 256;

	private String generateRandomString(int length) {
		StringBuilder returnValue = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
		}

		return new String(returnValue);
	}

	public String generateUserId(int length) {
		return generateRandomString(length);
	}

	public String getSalt(int length) {
		return generateRandomString(length);
	}

	public void validateRequiredFields(UserDTO userDto) throws MissingRequiredFieldException {
		if (userDto.getFirstName() == null 
				|| userDto.getFirstName().isEmpty() 
				|| userDto.getLastName() == null
				|| userDto.getLastName().isEmpty() 
				|| userDto.getEmail() == null 
				|| userDto.getEmail().isEmpty()
				|| userDto.getPassword() == null 
				|| userDto.getPassword().isEmpty()) {
			throw new MissingRequiredFieldException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		}
	}

	public String generateSecurePassword(String password, String salt) {
		String returnValue = null;

		byte[] securePassword = hash(password.toCharArray(), salt.getBytes());

		returnValue = Base64.getEncoder().encodeToString(securePassword);

		return returnValue;
	}

	public byte[] hash(char[] password, byte[] salt) {
		PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
		Arrays.fill(password, Character.MIN_VALUE);
		try {
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			return skf.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException e) {
			throw new AssertionError("Error : " + e.getMessage(), e);
		} catch (InvalidKeySpecException e) {
			throw new AssertionError("Error : " + e.getMessage(), e);
		} finally {
			spec.clearPassword();
		}
	}

	public String generateAccessToken(String encryptedPassword, String accessTokenMaterial) {
		byte[] accessToken = hash(encryptedPassword.toCharArray(), accessTokenMaterial.getBytes());
		String fullAccessToken = Base64.getEncoder().encodeToString(accessToken);
		return fullAccessToken;
	}
}
