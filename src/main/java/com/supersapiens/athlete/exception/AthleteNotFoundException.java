package com.supersapiens.athlete.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter

public class AthleteNotFoundException extends RuntimeException {
	private HttpStatus httpStatus = HttpStatus.NOT_FOUND;
	private String message;
	
    public AthleteNotFoundException(String message) {
        super(message);
    }

    public AthleteNotFoundException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
