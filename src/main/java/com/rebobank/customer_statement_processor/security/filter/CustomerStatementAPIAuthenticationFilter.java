package com.rebobank.customer_statement_processor.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ch.qos.logback.classic.Logger;

/**
 * Basic authentication filter to check whether provided credentials in base auth of request header is valid
 * @author vgavhane
 *
 */

@Component
public class CustomerStatementAPIAuthenticationFilter implements Filter{

	@Value("${customer.statement.processor.username}")
	private String username;
	@Value("${customer.statement.processor.password}")
	private String password;
	
	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final Logger logger = (Logger) LoggerFactory.getLogger(CustomerStatementAPIAuthenticationFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String creds = ((HttpServletRequest) request).getHeader(AUTHORIZATION_HEADER);
		String errorMessage = "";
		// if cred is null or empty throw authentication exception
		if (isNullOrEmpty(creds)) {
			errorMessage = "Provide username and password!!";
			logger.error(errorMessage);
			setUnauthorizedResponseStatus(response, errorMessage);
			//throw new UserAuthenticationException(HttpStatus.UNAUTHORIZED, "Provide username and password!!");
		} else if (creds.contains(":")) {
			String[] credentials = creds.split(":");
			if (credentials.length >= 2 && username.equals(credentials[0]) && password.equals(credentials[1])) {
				chain.doFilter(request, response);
			} else {
				errorMessage = "Provide username and password in valid format!!";
				logger.error(errorMessage);
				setUnauthorizedResponseStatus(response, errorMessage);
				//throw new UserAuthenticationException(HttpStatus.UNAUTHORIZED, "Provide username and password in valid format!!");
			}
		} else {
			errorMessage = "Provide username and password in valid format!!";
			logger.error(errorMessage);
			setUnauthorizedResponseStatus(response, errorMessage);
			//throw new UserAuthenticationException(HttpStatus.UNAUTHORIZED, "Provide username and password in valid format!!");
		}
	}
	
	private void setUnauthorizedResponseStatus(final ServletResponse response, String errorMessage) throws IOException {
		if (response instanceof HttpServletResponse) {
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			httpResponse.sendError(401, errorMessage);
		}
	}

	public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return false;
        return true;
    }

}
