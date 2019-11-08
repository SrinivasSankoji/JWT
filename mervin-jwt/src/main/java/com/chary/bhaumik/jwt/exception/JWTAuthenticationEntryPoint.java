package com.chary.bhaumik.jwt.exception;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.chary.bhaumik.jwt.enumeration.ErrorCode;
import com.chary.bhaumik.jwt.error.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint
{
	private final ObjectMapper mapper;

    @Autowired
    public JWTAuthenticationEntryPoint(ObjectMapper mapper) {
        this.mapper = mapper;
    }


	@Override
	public void commence(HttpServletRequest request, HttpServletResponse httpServletResponse,
			AuthenticationException authException) throws IOException, ServletException 
	{
		httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        this.mapper.writeValue(httpServletResponse.getWriter(),
                ErrorResponse.of("Sorry! Authentication was not possible",
                        ErrorCode.AUTHENTICATION,
                        HttpStatus.UNAUTHORIZED));
		
	}

}
