package com.chary.bhaumik.jwt.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chary.bhaumik.jwt.configuration.CustomException;
import com.chary.bhaumik.jwt.model.User;
import com.chary.bhaumik.jwt.repository.UserRepository;
import com.chary.bhaumik.jwt.security.JwtTokenProvider;

@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private UserRepository userRepository;


	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public String signin(String username, String password) 
	{
		try {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		return jwtTokenProvider.generateToken(userDetails);
		}catch (AuthenticationException e) {
		throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@Override
	public String signup(User user) 
	{
		if (!userRepository.existsByUsername(user.getUserName())) 
		{
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userRepository.save(user);
			return jwtTokenProvider.createToken(user.getUserName(), null);
		} else {
			throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@Override
	public void delete(String username) {
		userRepository.deleteByUserName(username);
	}

	@Override
	public User search(String username) 
	{
		Optional<User> user = userRepository.findByUserName(username);
		if (user == null) {
			throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
		}
		return user.get();
	}

	@Override
	public User whoami(HttpServletRequest req) 
	{
		return userRepository.findByUserName(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req))).get();
	}

	@Override
	public String refresh(String username) 
	{
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		return jwtTokenProvider.generateToken(userDetails);
	}

	@Override
	public boolean existsByUsername(String username) 
	{
		return userRepository.existsByUsername(username);
	}

}
