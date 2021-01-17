package com.rebobank.customer_statement_processor.dto;

import org.springframework.web.multipart.MultipartFile;

public class CustomerStatementDto extends BaseDto {

	private MultipartFile file;
	
	private CustomerStatementProcessorReportDto customerStatementProcessorReportDto = new CustomerStatementProcessorReportDto();

	public CustomerStatementProcessorReportDto getCustomerStatementProcessorReportDto() {
		return customerStatementProcessorReportDto;
	}

	public void setCustomerStatementProcessorReportDto(
			CustomerStatementProcessorReportDto customerStatementProcessorReportDto) {
		this.customerStatementProcessorReportDto = customerStatementProcessorReportDto;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
}
	