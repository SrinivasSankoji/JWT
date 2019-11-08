package com.chary.bhaumik.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chary.bhaumik.jwt.model.AuthenticationRequest;
import com.chary.bhaumik.jwt.model.AuthenticationResponse;
import com.chary.bhaumik.jwt.util.JWTUtil;

@RestController
public class JWTController 
{
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	@Lazy
	UserDetailsService userDetailsService;
	
	@Autowired
	JWTUtil jwtUtil;
	
	@GetMapping("/hello")
	public String hello()
	{
		return "Hello Bhaumik";
	}
	
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws Exception
	{
		try {
		authenticationManager.authenticate(
		new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		}
		catch (BadCredentialsException e) 
		{
			throw new Exception("Incorrect User Name or Password");
		}
		UserDetails userDetails=userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		String jwt=jwtUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	

}
