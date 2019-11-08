package com.chary.bhaumik.jwt.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chary.bhaumik.jwt.model.CustomUserDetails;
import com.chary.bhaumik.jwt.model.Person;
import com.chary.bhaumik.jwt.repository.PersonRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
	
	@Autowired
	PersonRepository personRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException 
	{
		Person person= personRepository.findByUserName(userName)
			.orElseThrow(() -> new UsernameNotFoundException("UserName Not Found"));
		return new CustomUserDetails(person.getUserName(), person.getPassword(),
				new ArrayList<>(person.getRoles()));
	}

}
