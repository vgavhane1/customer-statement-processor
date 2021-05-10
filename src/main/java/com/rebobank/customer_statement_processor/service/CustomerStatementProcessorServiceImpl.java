package com.rebobank.customer_statement_processor.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rebobank.customer_statement_processor.dto.CustomerStatementDto;
import com.rebobank.customer_statement_processor.dto.CustomerStatementProcessorReportDto;
import com.rebobank.customer_statement_processor.model.CustomerStatementRecord;
import com.rebobank.customer_statement_processor.utils.CustomCSVParser;
import com.rebobank.customer_statement_processor.utils.CustomXMLParser;

import ch.qos.logback.classic.Logger;
/**
 *  Implementation class of Service interface which defines the methods required for parsing and validating
 *  customer statements in xml or csv format
 *  @author vgavhane
 */
@Component
public class CustomerStatementProcessorServiceImpl implements CustomerStatementProcessorService {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(CustomerStatementProcessorServiceImpl.class);
	
	@Autowired
	private CustomCSVParser customCSVParser;
	
	@Autowired
	private CustomXMLParser customXMLParser;
	
	@Override
	public CustomerStatementProcessorReportDto validateUniqueTransactionReferences(List<CustomerStatementRecord> CustomerStatementRecords, CustomerStatementProcessorReportDto customerStatementProcessorReportDto) {
		Set<Long> transactionReferencesSet = new HashSet<Long>();
		String errorMessage = "";
		LinkedHashMap<Long, String> report = new LinkedHashMap<Long, String>();
		for (CustomerStatementRecord CustomerStatementRecord : CustomerStatementRecords) {
			if (!transactionReferencesSet.add(CustomerStatementRecord.getTransactionReference())) {
				errorMessage = "Transaction reference::" + CustomerStatementRecord.getTransactionReference()
						+ " is not unique!!";
				logger.error(errorMessage);
				report.put(CustomerStatementRecord.getTransactionReference(), errorMessage);

				//Note - We are not throwing below exception as we are generating the report in json format in case customer statements get failed during validation.
				//throw new CustomerStatementProcessorException(HttpStatus.BAD_REQUEST, errorMessage);
			}
		}
		logger.info("validation on unique transaction references is successful!!");
		customerStatementProcessorReportDto.setReport(report);
		return customerStatementProcessorReportDto;
	}

	@Override
	public CustomerStatementProcessorReportDto validateEndBalance(List<CustomerStatementRecord> CustomerStatementRecords, CustomerStatementProcessorReportDto customerStatementProcessorReportDto) {
		Set<Long> transactionReferencesSet = new HashSet<Long>();
		LinkedHashMap<Long, String> report = customerStatementProcessorReportDto.getReport();
		String errorMessage = "";
		for (CustomerStatementRecord CustomerStatementRecord : CustomerStatementRecords) {
			logger.info("For transction reference::" + CustomerStatementRecord.getTransactionReference());

			Double calculatedEndBalance = CustomerStatementRecord.getStartBalance()
					+ CustomerStatementRecord.getMutation();
			BigDecimal bd = new BigDecimal(calculatedEndBalance).setScale(2, RoundingMode.HALF_UP);
			calculatedEndBalance = bd.doubleValue();

			logger.info("End Balance : " + CustomerStatementRecord.getEndBalance());
			logger.info("Calculated End Balance : " + calculatedEndBalance);

			if (!CustomerStatementRecord.getEndBalance().equals(calculatedEndBalance)) {
				transactionReferencesSet.add(CustomerStatementRecord.getTransactionReference());
				errorMessage = "Transaction reference::" + CustomerStatementRecord.getTransactionReference()
						+ " is not having valid end balance expected is :: " + CustomerStatementRecord.getEndBalance()
						+ " and actual is" + calculatedEndBalance;
				report.put(CustomerStatementRecord.getTransactionReference(), errorMessage);
			}
		}

		if (transactionReferencesSet.size() > 0) {
			errorMessage = "Transaction references::" + transactionReferencesSet
					+ " is not having valid end balance!!";
			logger.error(errorMessage);

			//Note- We are not throwing below exception as we are generating the report in json format in case customer statements get failed during validation.
			//throw new CustomerStatementProcessorException(HttpStatus.BAD_REQUEST, errorMessage);
		}
		customerStatementProcessorReportDto.setReport(report);
		logger.info("validation on end balance is successful!!");
		
		return customerStatementProcessorReportDto;
	}

	@Override
	public CustomerStatementProcessorReportDto parseFileAndSave(CustomerStatementDto customerStatementDto)
			throws IOException {
		List<CustomerStatementRecord> customerStatementRecordList = null;
		CustomerStatementProcessorReportDto customerStatementProcessorReportDto = new CustomerStatementProcessorReportDto();
		if (customerStatementDto.getFile() != null) {
			if (customerStatementDto.getFile().getOriginalFilename().contains(".csv")) {
				customerStatementRecordList = customCSVParser.parse(customerStatementDto.getFile().getInputStream());
			} else if (customerStatementDto.getFile().getOriginalFilename().contains(".xml")) {
				customerStatementRecordList = customXMLParser.parse(customerStatementDto.getFile().getInputStream());
			}
		}
		customerStatementProcessorReportDto = validateCustomerStatementRecordsFromFile(customerStatementRecordList,
				customerStatementProcessorReportDto);
		return customerStatementProcessorReportDto;
	}

	private CustomerStatementProcessorReportDto validateCustomerStatementRecordsFromFile(List<CustomerStatementRecord> customerStatementRecordList, CustomerStatementProcessorReportDto customerStatementProcessorReportDto){
		logger.info("Validating unique transction references...");
		customerStatementProcessorReportDto = validateUniqueTransactionReferences(customerStatementRecordList, customerStatementProcessorReportDto);
		logger.info("Validating end balance...");
		customerStatementProcessorReportDto = validateEndBalance(customerStatementRecordList, customerStatementProcessorReportDto);
		
		return customerStatementProcessorReportDto;
	}
}
