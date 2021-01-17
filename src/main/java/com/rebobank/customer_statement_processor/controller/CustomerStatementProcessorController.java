package com.rebobank.customer_statement_processor.controller;

import java.io.IOException;

import javax.ws.rs.Consumes;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rebobank.customer_statement_processor.dto.CustomerStatementDto;
import com.rebobank.customer_statement_processor.dto.CustomerStatementProcessorReportDto;
import com.rebobank.customer_statement_processor.service.CustomerStatementProcessorService;

import ch.qos.logback.classic.Logger;
/**
 *  Controller class to process customer statements in the CSV or XML format
 *  @author vgavhane
 */
@RestController
@RequestMapping("customer-statement-processor")
public class CustomerStatementProcessorController {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(CustomerStatementProcessorController.class);
	
	@Autowired
	private CustomerStatementProcessorService customerStatementProcessorService;
	
	@PostMapping("csv")
	@Consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
	public CustomerStatementProcessorReportDto createCSVCustomerStatement(@ModelAttribute CustomerStatementDto csvCustomerStatementDto) throws IOException {
		// convert this dto to model object which we can persist into database
		// authentication and authorization layer
		if(csvCustomerStatementDto.getFile()!=null) {
			logger.info("Parsing csv file .."+ csvCustomerStatementDto.getFile().getOriginalFilename());
			customerStatementProcessorService.parseCsvAndSave(csvCustomerStatementDto);
			logger.info("Parsed csv file successfully .."+ csvCustomerStatementDto.getFile().getOriginalFilename());
		}
		return csvCustomerStatementDto.getCustomerStatementProcessorReportDto();
	}
	
	@PostMapping("xml")
	@Consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
	public CustomerStatementProcessorReportDto createXMLCustomerStatement(@ModelAttribute CustomerStatementDto xmlCustomerStatementDto) throws IOException {
		// convert this dto to model object which we can persist into database
		// authentication and authorization layer
		if(xmlCustomerStatementDto.getFile()!=null) {
		logger.info("Parsing xml file .."+ xmlCustomerStatementDto.getFile().getOriginalFilename());
		customerStatementProcessorService.parseXmlAndSave(xmlCustomerStatementDto);
		logger.info("Parsed xml file successfully .."+ xmlCustomerStatementDto.getFile().getOriginalFilename());
		}
		return xmlCustomerStatementDto.getCustomerStatementProcessorReportDto();
	}
}
