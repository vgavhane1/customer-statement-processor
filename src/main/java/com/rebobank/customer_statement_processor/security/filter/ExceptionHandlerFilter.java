package com.rebobank.customer_statement_processor.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rebobank.customer_statement_processor.exception.UserAuthenticationException;

/**
 * Exception Handler in case exception is thrown inside authentication filter and we need to return that to client
 * @author vgavhane
 *
 */

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (RuntimeException e) {

        	UserAuthenticationException userAuthenticationException = new UserAuthenticationException(HttpStatus.UNAUTHORIZED, e.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(convertObjectToJson(userAuthenticationException));
    }
}

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

}