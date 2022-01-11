package com.rebobank.customer_statement_processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
/**
 *  This is the main class of customer statement processor application.
 *  @author vgavhane 
 * 
 */
@SpringBootApplication
@OpenAPIDefinition
public class CustomerStatementProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerStatementProcessorApplication.class, args);
	}

}
