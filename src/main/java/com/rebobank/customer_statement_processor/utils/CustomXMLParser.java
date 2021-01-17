package com.rebobank.customer_statement_processor.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import com.rebobank.customer_statement_processor.model.CustomerStatementRecord;

//import com.rebobank.customer_statement_processor.model.XMLCustomerStatementRecord;

import ch.qos.logback.classic.Logger;

@Component
public class CustomXMLParser {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(CustomXMLParser.class);
	
	@Autowired
	private SAXParserHandler saxParserHandler;
	
	public List<CustomerStatementRecord> parse(InputStream inputStream) throws IOException {
		List<CustomerStatementRecord> customerStatementRecordList = new ArrayList<CustomerStatementRecord>();
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
	    try {
	        SAXParser saxParser = saxParserFactory.newSAXParser();
	        logger.debug("Parsing of customer statements records from xml file ..");
	        saxParser.parse(inputStream, saxParserHandler);
	        logger.debug("Parsing of customer statements records from xml file is successful!!");	
	        //Get CustomerStatementRecordList list
	        customerStatementRecordList = saxParserHandler.getCustomerStatementRecordList();
	        //print CustomerStatementRecord information
	        for(CustomerStatementRecord customerStatementRecord : customerStatementRecordList)
	        	logger.debug(customerStatementRecord.getAccountNumber());
	    } catch (ParserConfigurationException | SAXException | IOException e) {
	        e.printStackTrace();
	    }
		return customerStatementRecordList;
	}

}
