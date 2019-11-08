package com.chary.bhaumik.jwt.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidJWTTokenException extends AuthenticationException
{
	private static final long serialVersionUID = 1L;

	public InvalidJWTTokenException(String msg) 
	{
		super(msg);
	}

}
