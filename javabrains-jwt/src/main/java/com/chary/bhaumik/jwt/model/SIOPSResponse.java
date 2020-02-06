package com.chary.bhaumik.jwt.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class SIOPSResponse implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String timestamp;
	private String message;
	private int status;
	private String path;
	private String jwt;
	
	private Object responsePayload;

}
