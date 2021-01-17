package com.rebobank.customer_statement_processor.controller;

import java.io.IOException;

import javax.ws.rs.Consumes;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rebobank.customer_statement_processor.dto.CustomerStatementDto;
import com.rebobank.customer_statement_processor.service.CustomerStatementProcessorService;

import ch.qos.logback.classic.Logger;

@RestController
@RequestMapping("customer-statement-processor")
public class CustomerStatementProcessorController {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(CustomerStatementProcessorController.class);
	
	@Autowired
	private CustomerStatementProcessorService customerStatementProcessorService;
	
	
	@GetMapping("csv")
	public String getCustomerStatement() {
		return "stat";
	}

	@PostMapping("csv")
	@Consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
	public String createCSVCustomerStatement(@ModelAttribute CustomerStatementDto csvCustomerStatementDto) throws IOException {
		// convert this dto to model object which we can persist into database
		// authentication and authorization layer
		if(csvCustomerStatementDto.getFile().getOriginalFilename()!=null) {
			logger.info("Parsing csv file .."+ csvCustomerStatementDto.getFile().getOriginalFilename());
			customerStatementProcessorService.parseCsvAndSave(csvCustomerStatementDto);
			logger.info("Parsed csv file successfully .."+ csvCustomerStatementDto.getFile().getOriginalFilename());
		}
		return "successful";
	}
	
	@PostMapping("xml")
	@Consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
	public String createXMLCustomerStatement(@ModelAttribute CustomerStatementDto xmlCustomerStatementDto) throws IOException {
		// convert this dto to model object which we can persist into database
		// authentication and authorization layer
		if(xmlCustomerStatementDto.getFile().getOriginalFilename()!=null) {
		logger.info("Parsing xml file .."+ xmlCustomerStatementDto.getFile().getOriginalFilename());
		customerStatementProcessorService.parseXmlAndSave(xmlCustomerStatementDto);
		logger.info("Parsed xml file successfully .."+ xmlCustomerStatementDto.getFile().getOriginalFilename());
		}
		return "successful";
	}
}
