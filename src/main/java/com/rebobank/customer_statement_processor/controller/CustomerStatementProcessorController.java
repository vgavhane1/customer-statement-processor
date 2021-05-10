package com.rebobank.customer_statement_processor.controller;

import java.io.IOException;

import javax.ws.rs.Consumes;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rebobank.customer_statement_processor.dto.CustomerStatementDto;
import com.rebobank.customer_statement_processor.dto.CustomerStatementProcessorReportDto;
import com.rebobank.customer_statement_processor.exception.CustomerStatementProcessorException;
import com.rebobank.customer_statement_processor.service.CustomerStatementProcessorService;

import ch.qos.logback.classic.Logger;
/**
 *  Controller class to process customer statements in CSV or XML format
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
		CustomerStatementProcessorReportDto customerStatementProcessorReportDto = null;
		if(csvCustomerStatementDto.getFile()!=null) {
			if(!csvCustomerStatementDto.getFile().getOriginalFilename().contains(".csv")) {
				logger.error("Provide valid csv file for processing!!! "+csvCustomerStatementDto.getFile().getOriginalFilename());
				throw new CustomerStatementProcessorException(HttpStatus.BAD_REQUEST, "Provide valid csv file for processing!!! "+csvCustomerStatementDto.getFile().getOriginalFilename());
			}
			logger.info("Parsing csv file .."+ csvCustomerStatementDto.getFile().getOriginalFilename());
			customerStatementProcessorReportDto = customerStatementProcessorService.parseFileAndSave(csvCustomerStatementDto);
			logger.info("Parsed csv file successfully .."+ csvCustomerStatementDto.getFile().getOriginalFilename());
		} else {
			logger.error("Provide csv file for processing!!!");
			throw new CustomerStatementProcessorException(HttpStatus.BAD_REQUEST, "Provide csv file for processing!!!");
		}
		return customerStatementProcessorReportDto;
	}
	
	@PostMapping("xml")
	@Consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
	public CustomerStatementProcessorReportDto createXMLCustomerStatement(@ModelAttribute CustomerStatementDto xmlCustomerStatementDto) throws IOException {
		CustomerStatementProcessorReportDto customerStatementProcessorReportDto = null;
		if(xmlCustomerStatementDto.getFile()!=null) {
		logger.info("Parsing xml file .."+ xmlCustomerStatementDto.getFile().getOriginalFilename());
		if(!xmlCustomerStatementDto.getFile().getOriginalFilename().contains(".xml")) {
			logger.error("Provide valid xml file for processing!!! "+xmlCustomerStatementDto.getFile().getOriginalFilename());
			throw new CustomerStatementProcessorException(HttpStatus.BAD_REQUEST, "Provide valid xml file for processing!!! " +xmlCustomerStatementDto.getFile().getOriginalFilename());
		}
		customerStatementProcessorReportDto = customerStatementProcessorService.parseFileAndSave(xmlCustomerStatementDto);
		logger.info("Parsed xml file successfully .."+ xmlCustomerStatementDto.getFile().getOriginalFilename());
		} else {
			logger.error("Provide xml file for processing!!!");
			throw new CustomerStatementProcessorException(HttpStatus.BAD_REQUEST, "Provide xml file for processing!!!");
		}
		return customerStatementProcessorReportDto;
	}
}
