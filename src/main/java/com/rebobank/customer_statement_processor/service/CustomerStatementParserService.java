package com.rebobank.customer_statement_processor.service;

import java.io.IOException;

import com.rebobank.customer_statement_processor.dto.CustomerStatementDto;
import com.rebobank.customer_statement_processor.dto.CustomerStatementProcessorReportDto;

/**
 *  Service interface which declares the methods required for parsing 
 *  customer statements in various file format e.g XML, CSV.
 *  @author vgavhane
 */

public interface CustomerStatementParserService {
	public CustomerStatementProcessorReportDto parseFileAndSave(CustomerStatementDto csvCustomerStatementDto) throws IOException;
}
