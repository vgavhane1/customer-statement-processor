package com.rebobank.customer_statement_processor.security.filter;

import org.springframework.http.HttpStatus;

import com.rebobank.customer_statement_processor.exception.CustomerStatementProcessorException;

public class UserAuthenticationException extends CustomerStatementProcessorException {

	private static final long serialVersionUID = 7750335184950686978L;
	
	public UserAuthenticationException(HttpStatus status, String message) {
		super(status, message);
	}
}
