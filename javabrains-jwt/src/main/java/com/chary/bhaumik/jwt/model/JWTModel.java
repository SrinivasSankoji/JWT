package com.chary.bhaumik.jwt.model;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Data;

@Data
public class JWTModel implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	 private String sub;
	 private ArrayList<Role> role;
	 private String userName;
	 private long exp;
	 private long iat;

}
