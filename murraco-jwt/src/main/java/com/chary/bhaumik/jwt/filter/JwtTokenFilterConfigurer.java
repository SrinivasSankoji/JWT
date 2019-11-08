package com.chary.bhaumik.jwt.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.chary.bhaumik.jwt.security.JwtTokenProvider;

@Component
public class JwtTokenFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> 
{
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

    @Override
	public void configure(HttpSecurity http) throws Exception 
    {
	    JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenProvider);
	    http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
	 }
}
