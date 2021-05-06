package com.supersapiens.athlete.exception;

import java.util.Date;

import org.hibernate.PropertyValueException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AthleteNotFoundException.class)
    protected ResponseEntity<Object> handleAthleteException(AthleteNotFoundException e,
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
	
	@ExceptionHandler(PropertyValueException.class)
	protected ResponseEntity<Object> handlePropertyValueException(PropertyValueException e,
																	WebRequest request) {
		return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(),
				HttpStatus.BAD_REQUEST, request);
	}
	
	@Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        String requestURI = ((ServletWebRequest) request).getRequest().getRequestURI();
        ApiError error = ApiError.builder()
        		.timeStamp(new Date())
        		.errorType(status.getReasonPhrase())
        		.message(body != null ? body.toString() : ex.getMessage())
        		.path(requestURI).build();
        
        log.error("Error occured while processing request {}, with message:  {}", requestURI,ex.getMessage());
        log.error("Api error  {}",error);
        ResponseEntity<Object> responseEntity = super.handleExceptionInternal(ex, error, headers, status, request);

        return responseEntity;
    }
}
