package com.chary.bhaumik.jwt.model;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CustomUserDetails extends User
{
	private static final long serialVersionUID = 1L;
	
	private List<Role> roles;

	public CustomUserDetails(String username, String password, List<Role> roles) 
	{
		super(username, password, true,true,true,true,roles.stream()
		.map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));
		this.roles=roles;
	}


	public String[] getRoleJWT()
	{
		return this.roles.stream().map(Role::getName).toArray(String[]::new);
	}
}
