package com.rebobank.customer_statement_processor.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.rebobank.customer_statement_processor.exception.CustomerStatementProcessorException;

@Component
public class CustomerStatementAPIAuthenticationFilter implements Filter{

	@Value("${customer.statement.processor.username}")
	private String username;
	@Value("${customer.statement.processor.password}")
	private String password;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String creds = ((HttpServletRequest) request).getHeader("Authorization");
		// if cred is null throw authentication exception
		if(creds==null) {
			setUnauthorizedResponseStatus(response);
			throw new UserAuthenticationException(HttpStatus.UNAUTHORIZED,"user name and password not provided");
		} else if(creds.contains(":")) {
			String[] credentials = creds.split(":");
			if(credentials.length >= 2 && username.equals(credentials[0]) && password.equals(credentials[1])) {
				chain.doFilter(request, response);
			} else {
				setUnauthorizedResponseStatus(response);
				throw new UserAuthenticationException(HttpStatus.UNAUTHORIZED,"user name and password invalid format");
			}		
			
		} else {
			setUnauthorizedResponseStatus(response);
			throw new UserAuthenticationException(HttpStatus.UNAUTHORIZED,"user name and password invalid format");
		}
	}
	
	private void setUnauthorizedResponseStatus(final ServletResponse response) {
		if (response instanceof HttpServletResponse) {
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

}
