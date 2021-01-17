package com.rebobank.customer_statement_processor.service;

import java.io.IOException;
import java.util.List;

import com.rebobank.customer_statement_processor.dto.CustomerStatementDto;
import com.rebobank.customer_statement_processor.model.CustomerStatementRecord;

public interface CustomerStatementProcessorService {
	
	public void validateUniqueTransactionReferences(List<CustomerStatementRecord> CustomerStatementRecord);
	
	public void validateEndBalance(List<CustomerStatementRecord> CustomerStatementRecords);
	
	public List<String> getAllTransactionReferences();
	
	public void parseCsvAndSave(CustomerStatementDto csvCustomerStatementDto) throws IOException;
	
	public void parseXmlAndSave(CustomerStatementDto xmlCustomerStatementDto) throws IOException;
}
