package com.rebobank.customer_statement_processor.security.filter;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ch.qos.logback.classic.Logger;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
   
	@Value("${customer.statement.processor.username}")
	private String username;
	@Value("${customer.statement.processor.password}")
	private String password;
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(SecurityConfig.class);
	
	@Override
    protected void configure(HttpSecurity http) throws Exception 
    {
			http.csrf().disable().authorizeRequests().antMatchers("/").permitAll().anyRequest().authenticated().and().addFilterBefore(new APIAuthenticationFilter("/customer-statement-processor/**",authenticationManagerBean()), UsernamePasswordAuthenticationFilter.class);
    }
    
}
