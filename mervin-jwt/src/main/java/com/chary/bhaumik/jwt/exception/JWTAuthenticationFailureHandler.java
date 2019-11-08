package com.chary.bhaumik.jwt.exception;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.chary.bhaumik.jwt.enumeration.ErrorCode;
import com.chary.bhaumik.jwt.error.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler
{
	// This Class will be Executed when The Authentication Fails
	private final ObjectMapper mapper;

    @Autowired
    public JWTAuthenticationFailureHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, 
			HttpServletResponse httpServletResponse,
			AuthenticationException exception) throws IOException, ServletException 
	{
		try {
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            if (exception instanceof BadCredentialsException) {
                httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                this.mapper.writeValue(httpServletResponse.getWriter(),
                        ErrorResponse.of("Invalid username or password",
                                ErrorCode.AUTHENTICATION,
                                HttpStatus.UNAUTHORIZED));
            } else if (exception instanceof AuthAlreadyAuthenticatedException) {
                httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
                this.mapper.writeValue(httpServletResponse.getWriter(),
                        ErrorResponse.of("There is already a user authenticated, logout first",
                                ErrorCode.ALREADYAUTHENTICATED,
                                HttpStatus.FORBIDDEN));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.getMessage(), ex);
            throw new AuthenticationServiceException("");
        }
    }
}
