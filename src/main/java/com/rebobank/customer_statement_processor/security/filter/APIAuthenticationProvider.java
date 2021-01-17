package com.rebobank.customer_statement_processor.security.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class APIAuthenticationProvider implements AuthenticationProvider{

	@Value("${customer.statement.processor.username}")
	private String username;
	@Value("${customer.statement.processor.password}")
	private String password;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if(username.equals(authentication.getPrincipal()) && password.equals(authentication.getCredentials())) {
			//authentication.setAuthenticated(true);
			return authentication;
		} else {
			throw new AuthenticationServiceException("Invalid user name password");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
