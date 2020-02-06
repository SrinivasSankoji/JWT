package com.chary.bhaumik.jwt.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chary.bhaumik.jwt.model.User;


@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository
{
	@Autowired
	@Qualifier("primaryJdbcTemplate")
    private JdbcTemplate primaryJdbcTemplate;

	@Override
	public Optional<User> loadUserByUsername(String userName) 
	{
		String hql="SELECT USERNAME,PASSWORD,ENABLED,ROLES FROM USERS WHERE USERNAME = ?";
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
		User user = primaryJdbcTemplate.queryForObject(hql, rowMapper,userName);
		return Optional.of(user);
	}

}
