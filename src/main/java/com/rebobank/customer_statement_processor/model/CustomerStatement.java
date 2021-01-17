package com.rebobank.customer_statement_processor.model;

import org.springframework.web.multipart.MultipartFile;

import com.rebobank.customer_statement_processor.dto.BaseDto;

public class CustomerStatement extends BaseDto {

	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
}
	