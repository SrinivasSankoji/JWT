package com.chary.bhaumik.jwt.properties;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties
public class JWTProperties implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String secret;
	private int expiration;
	private String cookieName;
	private String rolesPropertyName;
	

}
