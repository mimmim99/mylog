package com.smstudy.mylog.common.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class ResultDto {

	private boolean result;
	private String message;
	//private int status;
	
	public ResultDto() {
		this.result = true;
		//this.status = 200;
	}
	
	public ResultDto(boolean result) {
		this.result = result;
	}
	
	public ResultDto(boolean result, String message) {
		this.result = result;
		this.message = message;
	}
	
}
