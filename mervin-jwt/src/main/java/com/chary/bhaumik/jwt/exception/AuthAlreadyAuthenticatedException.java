package com.chary.bhaumik.jwt.exception;

import org.springframework.security.authentication.AuthenticationServiceException;

public class AuthAlreadyAuthenticatedException extends AuthenticationServiceException
{
	private static final long serialVersionUID = 1L;

	public AuthAlreadyAuthenticatedException(String msg) 
	{
		super(msg);
	}
}
