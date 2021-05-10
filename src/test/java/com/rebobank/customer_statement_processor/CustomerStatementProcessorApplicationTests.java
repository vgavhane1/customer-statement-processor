package com.rebobank.customer_statement_processor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.rebobank.customer_statement_processor.dto.CustomerStatementDto;
import com.rebobank.customer_statement_processor.dto.CustomerStatementProcessorReportDto;
import com.rebobank.customer_statement_processor.model.CustomerStatementRecord;
import com.rebobank.customer_statement_processor.service.CustomerStatementProcessorService;
import com.rebobank.customer_statement_processor.service.CustomerStatementProcessorServiceImpl;

@RunWith(SpringRunner.class)
public class CustomerStatementProcessorApplicationTests {

	public CustomerStatementProcessorService customerStatementProcessorService;

	@Before
	public void setUp(){
		customerStatementProcessorService = new CustomerStatementProcessorServiceImpl();
	}

	@Test
	public void testValidateUniqueTransactionReferences(){
		List<CustomerStatementRecord> customerStatementRecords = new ArrayList<>();
		CustomerStatementProcessorReportDto customerStatementProcessorReportDto = new CustomerStatementProcessorReportDto();

		customerStatementProcessorReportDto.setReport(new LinkedHashMap<>());
		CustomerStatementDto customerStatementDto = new CustomerStatementDto();
		customerStatementDto.setCustomerStatementProcessorReportDto(customerStatementProcessorReportDto);
		CustomerStatementRecord customerStatementRecord = new CustomerStatementRecord();
		customerStatementRecord.setStartBalance(1234.00);
		customerStatementRecord.setMutation(1234.00);
		customerStatementRecord.setTransactionReference(1234l);
		customerStatementRecord.setEndBalance(2468.00);

		customerStatementRecords.add(customerStatementRecord);

		customerStatementDto = customerStatementProcessorService.validateEndBalance(customerStatementRecords, customerStatementDto);
		Assert.assertEquals(customerStatementDto.getCustomerStatementProcessorReportDto().getReport().size(), 0);
	}

}
