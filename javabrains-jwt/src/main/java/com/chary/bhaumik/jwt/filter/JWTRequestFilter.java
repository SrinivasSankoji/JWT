package com.chary.bhaumik.jwt.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.chary.bhaumik.jwt.session.RedisSession;
import com.chary.bhaumik.jwt.util.JWTUtil;

@Component
public class JWTRequestFilter extends OncePerRequestFilter
{
	@Autowired
	@Lazy
	UserDetailsService userDetailsService;
	
	@Autowired
	JWTUtil jwtUtil;
	
	@Autowired
	RedisSession redisSession;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException 
	{
		final String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwtToken);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) 
        {
        	UserDetails userDetails=redisSession.getUserDetails(username.toUpperCase());
            if (jwtUtil.validateToken(jwtToken, userDetails)) 
            {
            	Map<String,UserDetails> map=new HashMap<>();
        		map.put(jwtToken, userDetails);
            	redisSession.put(username.toUpperCase().concat("|")
        				.concat((new Date(System.currentTimeMillis())).toString()),map);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
