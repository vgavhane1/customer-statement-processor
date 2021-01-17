package com.rebobank.customer_statement_processor.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBeanBuilder;
import com.rebobank.customer_statement_processor.model.CustomerStatementRecord;

import ch.qos.logback.classic.Logger;



@Component
public class CustomCSVParser {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(CustomCSVParser.class);
	
	public List<CustomerStatementRecord> parse(InputStream inputStream) throws IOException {
		Reader reader = new InputStreamReader(inputStream);
		CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
		logger.debug("Parsing of customer statements records from csv file ..");
		List<CustomerStatementRecord> beans = new CsvToBeanBuilder<CustomerStatementRecord>(csvReader)
              .withType(CustomerStatementRecord.class)
              .build()
              .parse();
		logger.debug("Parsing of customer statements records from csv file is successful!!");		 
		return beans;
	}
}