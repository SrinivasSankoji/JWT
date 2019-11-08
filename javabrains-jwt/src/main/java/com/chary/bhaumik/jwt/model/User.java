package com.chary.bhaumik.jwt.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "USERS")
public class User implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	
	@Column(name = "USERNAME")
    private String userName;
	
	@Column(name = "PASSWORD")
    private String password;
	
	@Column(name = "ENABLED")
    private boolean active;
	
	@Column(name = "ROLES")
    private String roles;
	

}
