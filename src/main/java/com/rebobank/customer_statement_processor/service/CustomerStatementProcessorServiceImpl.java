package com.rebobank.customer_statement_processor.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.rebobank.customer_statement_processor.dto.CustomerStatementDto;
import com.rebobank.customer_statement_processor.exception.CustomerStatementProcessorException;
import com.rebobank.customer_statement_processor.model.CustomerStatementRecord;
import com.rebobank.customer_statement_processor.model.XMLCustomerStatementRecord;
import com.rebobank.customer_statement_processor.utils.CustomCSVParser;
import com.rebobank.customer_statement_processor.utils.CustomXMLParser;

import ch.qos.logback.classic.Logger;

@Component
public class CustomerStatementProcessorServiceImpl implements CustomerStatementProcessorService {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(CustomerStatementProcessorServiceImpl.class);
	
	@Autowired
	private CustomCSVParser customCSVParser;
	
	@Autowired
	private CustomXMLParser customXMLParser;
	
	@Override
	public void validateUniqueTransactionReferences(List<CustomerStatementRecord> CustomerStatementRecords) {
		Set<Long> transactionReferencesSet = new HashSet<Long>();
		for(CustomerStatementRecord CustomerStatementRecord:CustomerStatementRecords) {
			if(!transactionReferencesSet.add(CustomerStatementRecord.getTransactionReference())) {
				String errorMessage = "Transaction reference::"+CustomerStatementRecord.getTransactionReference()+" is not unique!!";
				logger.error(errorMessage);
				throw new CustomerStatementProcessorException(HttpStatus.BAD_REQUEST, errorMessage);
			}
		}
		logger.info("validation on unique transaction references is successful!!");
	}

	@Override
	public void validateEndBalance(List<CustomerStatementRecord> CustomerStatementRecords) {
		Set<Long> transactionReferencesSet = new HashSet<Long>();
		for(CustomerStatementRecord CustomerStatementRecord : CustomerStatementRecords) {
			logger.info("For transction reference::"+CustomerStatementRecord.getTransactionReference());
			

			 Double calculatedEndBalance = CustomerStatementRecord.getStartBalance() + CustomerStatementRecord.getMutation();
			 BigDecimal bd = new BigDecimal(calculatedEndBalance).setScale(2, RoundingMode.HALF_UP);
			 calculatedEndBalance = bd.doubleValue();
			
			logger.info("End Balance : "+ CustomerStatementRecord.getEndBalance());
			logger.info("Calculated End Balance : "+ calculatedEndBalance);
			
			if(!CustomerStatementRecord.getEndBalance().equals(calculatedEndBalance)) {
				transactionReferencesSet.add(CustomerStatementRecord.getTransactionReference());
			}
		}
		
		if(transactionReferencesSet.size() > 0) {
			String errorMessage = "Transaction references::"+transactionReferencesSet+" is not having valid end balance!!";
			logger.error(errorMessage);
			throw new CustomerStatementProcessorException(HttpStatus.BAD_REQUEST, errorMessage);
		}
		logger.info("validation on end balance is successful!!");
	}

	@Override
	public List<String> getAllTransactionReferences() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void parseCsvAndSave(CustomerStatementDto csvCustomerStatementDto) throws IOException {
		List<CustomerStatementRecord> CustomerStatementRecord = null;
		if(csvCustomerStatementDto.getFile()!=null)
		CustomerStatementRecord= customCSVParser.parse(csvCustomerStatementDto.getFile().getInputStream());	
		logger.info("Validating unique transction references...");
		validateUniqueTransactionReferences(CustomerStatementRecord);
		logger.info("Validating end balance...");
		validateEndBalance(CustomerStatementRecord);
	}

	@Override
	public void parseXmlAndSave(CustomerStatementDto xmlCustomerStatementDto) throws IOException {		
		List<CustomerStatementRecord> CustomerStatementRecord = null;
		if(xmlCustomerStatementDto.getFile()!=null)
		CustomerStatementRecord= customXMLParser.parse(xmlCustomerStatementDto.getFile().getInputStream());		
		logger.info("Validating unique transction references...");
		validateUniqueTransactionReferences(CustomerStatementRecord);
		logger.info("Validating end balance...");
		validateEndBalance(CustomerStatementRecord);
	}

}
