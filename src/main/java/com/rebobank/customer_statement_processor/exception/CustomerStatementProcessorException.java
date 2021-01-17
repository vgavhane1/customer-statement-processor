package com.rebobank.customer_statement_processor.exception;

import org.springframework.http.HttpStatus;

public class CustomerStatementProcessorException extends RuntimeException {
	
	private static final long serialVersionUID = -8709111105529417115L;
	private HttpStatus status;
	private String message;
	
	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public CustomerStatementProcessorException(HttpStatus status, String message) {		
		super(message);
		this.status = status;
		this.message= message;
		
	}
}
