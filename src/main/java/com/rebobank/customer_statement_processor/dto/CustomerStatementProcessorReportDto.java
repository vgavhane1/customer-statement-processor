package com.rebobank.customer_statement_processor.dto;

import java.util.LinkedHashMap;

public class CustomerStatementProcessorReportDto {
	private LinkedHashMap<Long, String> report;

	public LinkedHashMap<Long, String> getReport() {
		return report;
	}

	public void setReport(LinkedHashMap<Long, String> report) {
		this.report = report;
	}
	
	
}
