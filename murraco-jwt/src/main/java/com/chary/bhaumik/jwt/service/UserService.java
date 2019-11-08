package com.chary.bhaumik.jwt.service;

import javax.servlet.http.HttpServletRequest;

import com.chary.bhaumik.jwt.model.User;

public interface UserService 
{
	String signin(String username, String password);
	
	String signup(User user);
	
	void delete(String username);
	
	User search(String username);
	
	User whoami(HttpServletRequest req);
	
	String refresh(String username);
	
	boolean existsByUsername(String username);
}
