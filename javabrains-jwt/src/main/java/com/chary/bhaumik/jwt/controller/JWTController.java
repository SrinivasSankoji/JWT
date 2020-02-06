package com.chary.bhaumik.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chary.bhaumik.jwt.model.AuthenticationRequest;
import com.chary.bhaumik.jwt.model.AuthenticationResponse;
import com.chary.bhaumik.jwt.model.SIOPSResponse;
import com.chary.bhaumik.jwt.session.RedisSession;
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
	
	@Autowired
	RedisSession redisSession;
	
	@GetMapping("/hello")
	public String hello()
	{
		return "Hello Bhaumik";
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<SIOPSResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws Exception
	{
		try {
		UserDetails userDetails=userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		String jwtToken=jwtUtil.generateToken(userDetails);
		redisSession.put(authenticationRequest.getUsername().toUpperCase(), userDetails);
		SIOPSResponse siopsResponse=new SIOPSResponse();
		siopsResponse.setMessage("SUCCESS");
		siopsResponse.setStatus(200);
		siopsResponse.setResponsePayload(new AuthenticationResponse(jwtToken));
		return ResponseEntity.ok(siopsResponse);
		}
		catch (BadCredentialsException e) 
		{
			throw new BadCredentialsException("Incorrect User Name or Password");
		}
		
	}
	

}
