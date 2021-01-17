package com.rebobank.customer_statement_processor.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(CustomerStatementProcessorException.class)
	protected ResponseEntity<Object> handleDiscoveryException(
			CustomerStatementProcessorException ex) {
		return buildResponseEntity(ex);
	}
	
	private ResponseEntity<Object> buildResponseEntity(CustomerStatementProcessorException customerStatementProcessorException) {
		return new ResponseEntity<>(customerStatementProcessorException, customerStatementProcessorException.getStatus());
	}
}
