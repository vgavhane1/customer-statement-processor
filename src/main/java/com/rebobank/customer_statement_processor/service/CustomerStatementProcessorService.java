package com.rebobank.customer_statement_processor.service;

import java.util.List;

import com.rebobank.customer_statement_processor.dto.CustomerStatementProcessorReportDto;
import com.rebobank.customer_statement_processor.model.CustomerStatementRecord;

/**
 *  Service interface which declares the methods required for validating
 *  customer statements.
 *  @author vgavhane
 */

public interface CustomerStatementProcessorService extends CustomerStatementParserService {
	
	public CustomerStatementProcessorReportDto validateUniqueTransactionReferences(List<CustomerStatementRecord> CustomerStatementRecord, CustomerStatementProcessorReportDto customerStatementProcessorReportDto);
	
	public CustomerStatementProcessorReportDto validateEndBalance(List<CustomerStatementRecord> CustomerStatementRecords, CustomerStatementProcessorReportDto customerStatementProcessorReportDto);
}
