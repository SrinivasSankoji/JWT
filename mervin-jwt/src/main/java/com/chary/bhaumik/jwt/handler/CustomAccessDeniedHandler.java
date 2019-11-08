package com.chary.bhaumik.jwt.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.chary.bhaumik.jwt.enumeration.ErrorCode;
import com.chary.bhaumik.jwt.error.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler
{
	private final ObjectMapper mapper;

    @Autowired
    public CustomAccessDeniedHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse httpServletResponse,
			AccessDeniedException accessDeniedException) throws IOException, ServletException 
	{
		httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
        this.mapper.writeValue(httpServletResponse.getWriter(),
                ErrorResponse.of(
                        "Sorry, this resource is forbidden. Not sufficient permission.",
                        ErrorCode.FORBIDDENRESOURCE,
                        HttpStatus.FORBIDDEN));
    }

}
