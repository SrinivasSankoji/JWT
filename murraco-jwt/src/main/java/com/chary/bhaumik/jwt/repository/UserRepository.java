package com.chary.bhaumik.jwt.repository;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.chary.bhaumik.jwt.model.User;


public interface UserRepository 
{
	Optional<User> findByUserName(String userName);
	 
	String signin(String username, String password);
		
	String signup(User user);
	
	void delete(String username);
	
	User search(String username);
	
	User whoami(HttpServletRequest req);
	
	String refresh(String username);
	
	boolean existsByUsername(String username);

	void save(User user);

	void deleteByUserName(String username);
}
