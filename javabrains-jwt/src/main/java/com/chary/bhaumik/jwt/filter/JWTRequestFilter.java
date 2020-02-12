package com.chary.bhaumik.jwt.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.chary.bhaumik.jwt.model.JWTModel;
import com.chary.bhaumik.jwt.session.RedisSession;
import com.chary.bhaumik.jwt.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;

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
        String userName = null;
        String jwtToken = null;
        long expirationTime=0;

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) 
		{
			try {
				jwtToken = authorizationHeader.substring(7);
				userName = jwtUtil.extractUsername(jwtToken);
			} catch (ExpiredJwtException exception) {
				JWTModel jwtModel = decodeJWT(jwtToken);
				userName = jwtModel.getUserName().toUpperCase();
				expirationTime = jwtModel.getExp();
				refreshToken(userName, expirationTime);
				jwtToken=redisSession.getJWTToken(userName);
			}
		}

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) 
        {
        	UserDetails userDetails=redisSession.getUserDetails(userName.toUpperCase());
            if (jwtUtil.validateToken(jwtToken, userDetails)) 
            {
            	Map<String, UserDetails> tokenMap=new HashMap<>();
        		tokenMap.put(jwtToken, userDetails);
        		Map<Long,Map<String, UserDetails>> refreshMap=new HashMap<>();
        		refreshMap.put(System.currentTimeMillis(), tokenMap);
        		redisSession.put(userDetails.getUsername().toUpperCase(), refreshMap);
            	
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
	
	
	private JWTModel decodeJWT(String jwtToken) throws IOException
	{
		String[] splitstring = jwtToken.split("\\.");
        String base64EncodedBody = splitstring[1];
        Base64 base64Url = new Base64(true);
        String body = new String(base64Url.decode(base64EncodedBody));
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.readValue(body, JWTModel.class);
	}
	
	private String refreshToken(String userName,long expirationTime)
	{
		long lastRequest = redisSession.getLastRequestofUser(userName);
		long diff = (new Date(lastRequest).getTime() - new Date(expirationTime).getTime()) / (60 * 1000) % 60;
		if (diff < 1) {
			Map<String, UserDetails> tokenMap=new HashMap<>();
			tokenMap.put(jwtUtil.generateToken(redisSession.getUserDetails(userName)),redisSession.getUserDetails(userName));
			Map<Long,Map<String, UserDetails>> refreshMap=new HashMap<>();
			refreshMap.put(System.currentTimeMillis(), tokenMap);
			redisSession.put(userName.toUpperCase(), refreshMap);
		}
		return redisSession.getJWTToken(userName);
	}
}
