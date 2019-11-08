package com.chary.bhaumik.jwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chary.bhaumik.jwt.model.Person;

public interface PersonRepository extends JpaRepository<Person,Long>
{
	Optional<Person> findByUserName(String UserName);
}
