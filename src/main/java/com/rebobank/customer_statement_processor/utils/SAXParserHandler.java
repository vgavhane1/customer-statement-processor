package com.rebobank.customer_statement_processor.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.rebobank.customer_statement_processor.model.CustomerStatementRecord;

//import com.rebobank.customer_statement_processor.model.XMLCustomerStatementRecord;


@Component
public class SAXParserHandler extends DefaultHandler {
		private List<CustomerStatementRecord> customerStatementRecordList = null;
		private CustomerStatementRecord customerStatementRecord = null;
		private StringBuilder data = null;

		
		public List<CustomerStatementRecord> getCustomerStatementRecordList() {
			return customerStatementRecordList;
		}

		boolean accountNumber = false;
		boolean startBalance = false;
		boolean mutation = false;
		boolean description = false;
		boolean endBalance = false;
		

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

			if (qName.equalsIgnoreCase("record")) {
				// create a new CustomerStatementRecord and put it in Map
				String transactionReference = attributes.getValue("reference");
				// initialize Employee object and set id attribute
				customerStatementRecord = new CustomerStatementRecord();
				customerStatementRecord.setTransactionReference(Long.parseLong(transactionReference));
				// initialize list
				if (customerStatementRecordList == null)
					customerStatementRecordList = new ArrayList<>();
			} else if (qName.equalsIgnoreCase("accountNumber")) {
				// set boolean values for fields, will be used in setting CustomerStatementRecord variables
				accountNumber = true;
			} else if (qName.equalsIgnoreCase("startBalance")) {
				startBalance = true;
			} else if (qName.equalsIgnoreCase("mutation")) {
				mutation = true;
			} else if (qName.equalsIgnoreCase("description")) {
				description = true;
			}else if (qName.equalsIgnoreCase("endBalance")) {
				endBalance = true;
			}
			// create the data container
			data = new StringBuilder();
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if (accountNumber) {
				customerStatementRecord.setAccountNumber(data.toString());
				accountNumber = false;
			} else if (startBalance) {
				customerStatementRecord.setStartBalance(Double.parseDouble(data.toString()));
				startBalance = false;
			} else if (mutation) {
				customerStatementRecord.setMutation(Double.parseDouble(data.toString()));
				mutation = false;
			} else if (description) {
				customerStatementRecord.setDescription(data.toString());
				description = false;
			}else if (endBalance) {
				customerStatementRecord.setEndBalance(Double.parseDouble(data.toString()));
				endBalance = false;
			}
			
			if (qName.equalsIgnoreCase("record")) {
				// add CustomerStatementRecord object to list
				customerStatementRecordList.add(customerStatementRecord);
			}
		}

		@Override
		public void characters(char ch[], int start, int length) throws SAXException {
			data.append(new String(ch, start, length));
		}
}
