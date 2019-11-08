package com.chary.bhaumik.jwt.repository;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

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
	public Optional<User> findByUserName(String userName) 
	{
		String hql="SELECT USERNAME,PASSWORD,ENABLED,ROLES FROM USERS WHERE USERNAME = ?";
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
		User user = primaryJdbcTemplate.queryForObject(hql, rowMapper,userName);
		return Optional.of(user);
	}

	@Override
	public String signin(String username, String password) 
	{
		return null;
	}

	@Override
	public String signup(User user)
	{
		return null;
	}

	@Override
	public void delete(String username) 
	{
		
	}

	@Override
	public User search(String username) 
	{
		return null;
	}

	@Override
	public User whoami(HttpServletRequest req) 
	{
		return null;
	}

	@Override
	public String refresh(String username) 
	{
		return null;
	}

	@Override
	public boolean existsByUsername(String username)
	{
		return false;
	}

	@Override
	public void save(User user)
	{
		
	}

	@Override
	public void deleteByUserName(String username)
	{
		
	}

}
