package com.rebobank.customer_statement_processor.security.filter;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class APIAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	
	public APIAuthenticationFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url), authManager);
		//setAuthenticationManager(authManager);
	}
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		
		String creds = ((HttpServletRequest) request).getHeader("Authorization");
		// if cred is null throw authentication exception
		if(creds==null) {
			throw new AuthenticationServiceException("user name and password not provided");
		} else if(creds.contains(":")) {
			String[] credentials = creds.split(":");
			return new UsernamePasswordAuthenticationToken(credentials[0], credentials[1]);
		} else {
			throw new AuthenticationServiceException("user name and password invalid format");
		}
	}
	
	/*public APIAuthenticationFilter(AuthenticationManager authManager) {
		setAuthenticationManager(authManager);
	}
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		String creds = ((HttpServletRequest) request).getHeader("Authorization");
		// if cred is null throw authentication exception
		if(creds==null) {
			throw new AuthenticationServiceException("user name and password not provided");
		} else if(creds.contains(":")) {
			String[] credentials = creds.split(":");
			return new UsernamePasswordAuthenticationToken(credentials[0], credentials[1]);
		} else {
			throw new AuthenticationServiceException("user name and password invalid format");
		}
		
	}*/

}
