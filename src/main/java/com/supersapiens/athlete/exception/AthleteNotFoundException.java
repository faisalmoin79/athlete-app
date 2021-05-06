package com.supersapiens.athlete.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter

public class AthleteNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpStatus httpStatus = HttpStatus.NOT_FOUND;
	private String message;
	
    public AthleteNotFoundException(String message) {
        super(message);
        this.message=message;
    }

    public AthleteNotFoundException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
