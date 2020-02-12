package com.chary.bhaumik.jwt.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Hello implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String lastRequestTime;

}
