package com.student.student_service.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private String errorCode;
	private String errorDesc;

	public ResourceNotFoundException() {
		super();
	}
	
	public ResourceNotFoundException(String message) {
		super(message);
	}
	
	public ResourceNotFoundException(String errorCode, String errorDesc) {
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
	}
	
	public ResourceNotFoundException(String message, String errorCode, String errorDesc) {
		super(message);
		this.errorCode = errorCode;
		this.errorDesc = errorDesc;
	}
}
