package com.smstudy.mylog.common.dto;

import lombok.Data;

@Data
public class ServiceResultDto {

	boolean result;
	String message;
	
	public ServiceResultDto() {
		this.result = true;
	}
	
	public ServiceResultDto(boolean result) {
		this.result = result;
	}
	
	public ServiceResultDto(boolean result, String message) {
		this.result = result;
		this.message = message;
	}
	
}
