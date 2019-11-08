package com.chary.bhaumik.jwt.repository;

import java.util.Optional;

import com.chary.bhaumik.jwt.model.User;


public interface UserRepository 
{
	 Optional<User> loadUserByUsername(String userName);
}
