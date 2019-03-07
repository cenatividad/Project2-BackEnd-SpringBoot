package com.revature.util;

import org.springframework.http.HttpStatus;

public class HttpClientErrorException extends Throwable {
	HttpStatus status;
	String message;
	
	public HttpClientErrorException(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
}
