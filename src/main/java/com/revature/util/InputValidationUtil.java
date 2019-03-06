package com.revature.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.models.User;

/**
 * Utility class that performs input validation for entity creation.
 */
public class InputValidationUtil {
	/**
	 * Checks for email having the correct length.
	 * TODO: Verify that it also meets the syntax of a valid email
	 */
	public static void isEmailOK(String email) {
		try {
			InputValidationUtil.isStringOK(email, User.MIN_EMAIL_LENGTH, User.MAX_EMAIL_LENGTH);
		} catch (HttpClientErrorException e) {
			throw new HttpClientErrorException(e.getStatusCode(), ("Email: " + e.getMessage()));
		}
	}

	/**
	 * Checks that the passed username length is within the acceptable parameters
	 */
	public static void isUsernameOK(String username) {
		try {
			InputValidationUtil.isStringOK(username, User.MIN_USERNAME_LENGTH, User.MAX_USERNAME_LENGTH);
		} catch (HttpClientErrorException e) {
			throw new HttpClientErrorException(e.getStatusCode(), ("Username: " + e.getMessage()));
		}
	}

	/**
	 * Checks that the passed password length is within the acceptable parameters
	 */
	public static void isPasswordOK(String password) {
		try {
			InputValidationUtil.isStringOK(password, User.MIN_PASSWORD_LENGTH, User.MAX_PASSWORD_LENGTH);
		} catch (HttpClientErrorException e) {
			throw new HttpClientErrorException(e.getStatusCode(), ("Password: " + e.getMessage()));
		}
	}

	/**
	 * Checks that the passed first name length is within the acceptable parameters
	 */
	public static void isFirstNameOK(String firstName) {
		try {
			InputValidationUtil.isStringOK(firstName, User.MIN_FIRST_NAME_LENGTH, User.MAX_FIRST_NAME_LENGTH);
		} catch (HttpClientErrorException e) {
			throw new HttpClientErrorException(e.getStatusCode(), ("First Name: " + e.getMessage()));
		}
	}

	/**
	 * Checks that the passed last name length is within the acceptable parameters
	 */
	public static void isLastNameOK(String lastName) {
		try {
			InputValidationUtil.isStringOK(lastName, User.MIN_LAST_NAME_LENGTH, User.MAX_LAST_NAME_LENGTH);
		} catch (HttpClientErrorException e) {
			throw new HttpClientErrorException(e.getStatusCode(), ("Last Name: " + e.getMessage()));
		}
	}
	
	/**
	 * Common chunk of code to verify that the passed string is within the specified limits.
	 * Used by all other functions in this class for string input validation.
	 */
	public static void isStringOK(String string, int min, int max) {
		if (string == null)
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "string is null");
		if (string.length() < min)
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "string is under the minimum length");
		if (string.length() > max)
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "string is over the maximum length");
	}
}
