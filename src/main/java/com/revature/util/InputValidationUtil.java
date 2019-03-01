package com.revature.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.models.User;

public class InputValidationUtil {
	public static void isEmailOK(String email) {
		try {
			InputValidationUtil.isStringOK(email, User.MIN_EMAIL_LENGTH, User.MAX_EMAIL_LENGTH);
		} catch (HttpClientErrorException e) {
			throw new HttpClientErrorException(e.getStatusCode(), ("Email: " + e.getMessage()));
		}
	}

	public static void isUsernameOK(String username) {
		try {
			InputValidationUtil.isStringOK(username, User.MIN_USERNAME_LENGTH, User.MAX_USERNAME_LENGTH);
		} catch (HttpClientErrorException e) {
			throw new HttpClientErrorException(e.getStatusCode(), ("Username: " + e.getMessage()));
		}
	}

	public static void isPasswordOK(String password) {
		try {
			InputValidationUtil.isStringOK(password, User.MIN_PASSWORD_LENGTH, User.MAX_PASSWORD_LENGTH);
		} catch (HttpClientErrorException e) {
			throw new HttpClientErrorException(e.getStatusCode(), ("Password: " + e.getMessage()));
		}
	}

	public static void isFirstNameOK(String firstName) {
		try {
			InputValidationUtil.isStringOK(firstName, User.MIN_FIRST_NAME_LENGTH, User.MAX_FIRST_NAME_LENGTH);
		} catch (HttpClientErrorException e) {
			throw new HttpClientErrorException(e.getStatusCode(), ("First Name: " + e.getMessage()));
		}
	}

	public static void isLastNameOK(String lastName) {
		try {
			InputValidationUtil.isStringOK(lastName, User.MIN_LAST_NAME_LENGTH, User.MAX_LAST_NAME_LENGTH);
		} catch (HttpClientErrorException e) {
			throw new HttpClientErrorException(e.getStatusCode(), ("Last Name: " + e.getMessage()));
		}
	}
	
	public static void isStringOK(String string, int min, int max) {
		if (string == null)
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "string is null");
		if (string.length() < min)
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "string is under the minimum length");
		if (string.length() > max)
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "string is over the maximum length");
	}
}
