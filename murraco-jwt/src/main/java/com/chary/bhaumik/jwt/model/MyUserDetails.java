package com.chary.bhaumik.jwt.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class MyUserDetails implements UserDetails
{
	private static final long serialVersionUID = 1L;
	
	private String userName;
    private String password;
    private boolean active;
    private List<GrantedAuthority> authorities;
	
    public MyUserDetails(User user) 
    {
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.active = user.isActive();
        this.authorities = Arrays.stream(user.getRoles().split(","))
                    	   .map(SimpleGrantedAuthority::new)
                    	   .collect(Collectors.toList());
    }
    
	/**private String userName;
    public MyUserDetails(String userName)
    {
    	this.userName=userName;
    }**/

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() 
	{
		return authorities;
	}
	
    /**public Collection<? extends GrantedAuthority> getAuthorities() 
	{
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}**/
    
	@Override
	public String getPassword() 
	{
		//return "pass";
		return password;
	}

	@Override
	public String getUsername() 
	{
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() 
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() 
	{
		return true;
	}

	@Override
	public boolean isEnabled() 
	{
		//return true;
		return true;
	}

}
