package com.supersapiens.athlete.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AthleteNotFoundException.class)
    protected ResponseEntity<Object> handleCustomerException(AthleteNotFoundException e,
                                                                    WebRequest request) {
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(),
                e.getHttpStatus(), request);
    }
    
	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e,
																	WebRequest request) {
		return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(),
				HttpStatus.BAD_REQUEST, request);
	}
}
