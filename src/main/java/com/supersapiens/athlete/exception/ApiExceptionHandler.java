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
	
	@Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        ApiError error = new ApiError();
        error.setTimeStamp(new Date());
        error.setStatus(status.value());
        error.setError(status.getReasonPhrase());
        error.setMessage(body != null ? body.toString() : ex.getMessage());
        
        String requestURI = ((ServletWebRequest) request).getRequest().getRequestURI();
        error.setPath(requestURI);
        
        log.debug("error occured with these details {}",error);
        log.error("Error occured while processing request {}, with error:  {}", requestURI,ex.getMessage());
        ResponseEntity<Object> responseEntity = super.handleExceptionInternal(ex, error, headers, status, request);

        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		ReqTransactionTypesEnum transactionType = (ReqTransactionTypesEnum) requestAttributes.getAttribute(TRANSACTION_TYPE, RequestAttributes.SCOPE_REQUEST);
        if (transactionType != null) {
        	String logType = (String)requestAttributes.getAttribute(LOG_TYPE, RequestAttributes.SCOPE_REQUEST);
        	boolean inComing = StringUtils.isNotEmpty(logType) && API_LOG_TYPE_OUTGOING.equals(logType) ? false: true;

            BaseTransactionLog baseTransactionLog = transactionLogService.saveTransactionLog(null, inComing,
                    transactionType, status);
            error.setTrackingNumber(baseTransactionLog instanceof IncomingTransactionLog ?
                    ((IncomingTransactionLog) baseTransactionLog).getIncomingTransactionId()
                    : ((OutgoingTransactionLog) baseTransactionLog).getOutgoingTransactionId());
        }
        return responseEntity;
    }
}
