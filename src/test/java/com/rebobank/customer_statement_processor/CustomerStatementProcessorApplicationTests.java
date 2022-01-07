package com.rebobank.customer_statement_processor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rebobank.customer_statement_processor.dto.CustomerStatementProcessorReportDto;
import com.rebobank.customer_statement_processor.model.CustomerStatementRecord;
import com.rebobank.customer_statement_processor.service.CustomerStatementProcessorService;

@SpringBootTest
public class CustomerStatementProcessorApplicationTests {

	@Autowired
	public CustomerStatementProcessorService customerStatementProcessorService;

	@Test
	public void testValidateEndBalance(){
		List<CustomerStatementRecord> customerStatementRecords = new ArrayList<>();
		CustomerStatementProcessorReportDto customerStatementProcessorReportDto = new CustomerStatementProcessorReportDto();
		customerStatementProcessorReportDto.setReport(new LinkedHashMap<>());
		CustomerStatementRecord customerStatementRecord = new CustomerStatementRecord();
		customerStatementRecord.setStartBalance(1234.00);
		customerStatementRecord.setMutation(1234.00);
		customerStatementRecord.setTransactionReference(1234l);
		customerStatementRecord.setEndBalance(2468.00);

		customerStatementRecords.add(customerStatementRecord);

		customerStatementProcessorReportDto = customerStatementProcessorService.validateEndBalance(customerStatementRecords, customerStatementProcessorReportDto);
		Assertions.assertEquals(customerStatementProcessorReportDto.getReport().size(), 0);
	}

	@Test
	public void testWrongValidateEndBalance(){
		List<CustomerStatementRecord> customerStatementRecords = new ArrayList<>();
		CustomerStatementProcessorReportDto customerStatementProcessorReportDto = new CustomerStatementProcessorReportDto();
		customerStatementProcessorReportDto.setReport(new LinkedHashMap<>());
		CustomerStatementRecord customerStatementRecord = new CustomerStatementRecord();
		customerStatementRecord.setStartBalance(1234.00);
		customerStatementRecord.setMutation(1234.00);
		customerStatementRecord.setTransactionReference(1234l);
		customerStatementRecord.setEndBalance(2469.00);

		customerStatementRecords.add(customerStatementRecord);

		customerStatementProcessorReportDto = customerStatementProcessorService.validateEndBalance(customerStatementRecords, customerStatementProcessorReportDto);
		Assertions.assertEquals(customerStatementProcessorReportDto.getReport().size(), 1);
	}

	@Test
	public void testValidateUniqueTransactionReferences(){
		List<CustomerStatementRecord> customerStatementRecords = new ArrayList<>();
		CustomerStatementProcessorReportDto customerStatementProcessorReportDto = new CustomerStatementProcessorReportDto();
		customerStatementProcessorReportDto.setReport(new LinkedHashMap<>());
		CustomerStatementRecord customerStatementRecord = new CustomerStatementRecord();
		customerStatementRecord.setStartBalance(1234.00);
		customerStatementRecord.setMutation(1234.00);
		customerStatementRecord.setTransactionReference(1234l);
		customerStatementRecord.setEndBalance(2468.00);

		CustomerStatementRecord customerStatementRecord1 = new CustomerStatementRecord();
		customerStatementRecord1.setTransactionReference(12345l);

		customerStatementRecords.add(customerStatementRecord);
		customerStatementRecords.add(customerStatementRecord1);

		customerStatementProcessorReportDto = customerStatementProcessorService.validateUniqueTransactionReferences(customerStatementRecords, customerStatementProcessorReportDto);
		Assertions.assertEquals(customerStatementProcessorReportDto.getReport().size(), 0);
	}

	@Test
	public void testValidateDuplicateTransactionReferences(){
		List<CustomerStatementRecord> customerStatementRecords = new ArrayList<>();
		CustomerStatementProcessorReportDto customerStatementProcessorReportDto = new CustomerStatementProcessorReportDto();
		customerStatementProcessorReportDto.setReport(new LinkedHashMap<>());
		CustomerStatementRecord customerStatementRecord = new CustomerStatementRecord();
		customerStatementRecord.setStartBalance(1234.00);
		customerStatementRecord.setMutation(1234.00);
		customerStatementRecord.setTransactionReference(1234l);
		customerStatementRecord.setEndBalance(2468.00);

		CustomerStatementRecord customerStatementRecord1 = new CustomerStatementRecord();
		customerStatementRecord1.setTransactionReference(1234l);

		customerStatementRecords.add(customerStatementRecord);
		customerStatementRecords.add(customerStatementRecord1);

		customerStatementProcessorReportDto = customerStatementProcessorService.validateUniqueTransactionReferences(customerStatementRecords, customerStatementProcessorReportDto);
		Assertions.assertEquals(customerStatementProcessorReportDto.getReport().size(), 1);
	}
}
