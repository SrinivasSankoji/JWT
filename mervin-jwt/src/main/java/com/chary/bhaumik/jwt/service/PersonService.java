package com.chary.bhaumik.jwt.service;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chary.bhaumik.jwt.model.Person;
import com.chary.bhaumik.jwt.model.Role;
import com.chary.bhaumik.jwt.repository.PersonRepository;

@Service
public class PersonService 
{
	@Autowired
	PersonRepository personRepository;
	
	@Transactional(readOnly = true)
	public Optional<Person> findByUserName(String UserName)
	{
		return this.personRepository.findByUserName(UserName);
	}
	
	@Transactional
	public void insertData()
	{
		BCryptPasswordEncoder byBCryptPasswordEncoder=new BCryptPasswordEncoder();
		
		Person personAllRoles=Person.builder().userName("user").
							  password(byBCryptPasswordEncoder.encode("hello")).build();
		
		Person admin=Person.builder().userName("admin").
				  password(byBCryptPasswordEncoder.encode("admin")).build();
		
		Person personPublic=Person.builder().userName("public").
				  password(byBCryptPasswordEncoder.encode("public")).build();
		
		Role roleAdmin=Role.builder().name("ADMIN").build();
		Role rolePublic=Role.builder().name("PUBLIC").build();
		
		personAllRoles.getRoles().add(roleAdmin);
		personAllRoles.getRoles().add(rolePublic);
		
		admin.getRoles().add(roleAdmin);
		personPublic.getRoles().add(rolePublic);
		this.personRepository.save(personAllRoles);
		this.personRepository.save(admin);
		this.personRepository.save(personPublic);
	}
	

}
