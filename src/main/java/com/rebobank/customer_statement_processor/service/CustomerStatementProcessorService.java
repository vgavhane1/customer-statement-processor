package com.rebobank.customer_statement_processor.service;

import java.io.IOException;
import java.util.List;

import com.rebobank.customer_statement_processor.dto.CustomerStatementDto;
import com.rebobank.customer_statement_processor.model.CustomerStatementRecord;

/**
 *  Service interface which declares the methods required for parsing and validating
 *  customer statements in xml or csv format
 *  @author vgavhane
 */

public interface CustomerStatementProcessorService {
	
	public CustomerStatementDto validateUniqueTransactionReferences(List<CustomerStatementRecord> CustomerStatementRecord, CustomerStatementDto customerStatementDto);
	
	public CustomerStatementDto validateEndBalance(List<CustomerStatementRecord> CustomerStatementRecords, CustomerStatementDto customerStatementDto);
	
	public CustomerStatementDto parseCsvAndSave(CustomerStatementDto csvCustomerStatementDto) throws IOException;
	
	public CustomerStatementDto parseXmlAndSave(CustomerStatementDto xmlCustomerStatementDto) throws IOException;
}
