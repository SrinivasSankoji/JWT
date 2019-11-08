package com.chary.bhaumik.jwt.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;

}
