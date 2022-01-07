package com.rebobank.customer_statement_processor.exception;


import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import net.minidev.json.JSONObject;
/**
 *  Global exception handler for all exceptions which will finally send to Http Client.
 *  @author vgavhane
 */

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(CustomerStatementProcessorException.class)
	protected ResponseEntity<Object> handleCustomerStatementProcessorException(
			CustomerStatementProcessorException ex) {
		return buildResponseEntity(ex);
	}
	
	private ResponseEntity<Object> buildResponseEntity(CustomerStatementProcessorException customerStatementProcessorException) {
		
		HttpHeaders httpResponseHeaders = new HttpHeaders();
		httpResponseHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpResponseHeaders.set("X-ERROR","true");
		
		JSONObject resonseBody = new JSONObject();
		resonseBody.put("message", customerStatementProcessorException.getMessage());
		resonseBody.put("status", customerStatementProcessorException.getStatus());
		
		return new ResponseEntity<>(resonseBody, httpResponseHeaders, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UserAuthenticationException.class)
	public ResponseEntity<Object> handleUserAuthenticationException(UserAuthenticationException ex,
			HttpServletResponse response) {
		return new ResponseEntity<>(ex, ex.getStatus());
	}
}
