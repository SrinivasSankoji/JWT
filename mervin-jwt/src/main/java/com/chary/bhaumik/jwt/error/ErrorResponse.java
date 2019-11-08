package com.chary.bhaumik.jwt.error;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.chary.bhaumik.jwt.enumeration.ErrorCode;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class ErrorResponse 
{
	// HTTP Response Status Code
	private HttpStatus status;
	
	// General Error message
	private String message;
	
	 // Error code
	private ErrorCode errorCode;
	
	@JsonIgnore
	private final LocalDateTime timeStamp;
	
	private ErrorResponse(final String message, final ErrorCode errorCode, HttpStatus status) {
        this.message = message;
        this.errorCode = errorCode;
        this.status = status;
        this.timeStamp = LocalDateTime.now();
    }

    public static ErrorResponse of(final String message, final ErrorCode errorCode, HttpStatus status) {
        return new ErrorResponse(message, errorCode, status);
    }

	public Integer getStatus()
	{
		return this.status.value();
	}

}
