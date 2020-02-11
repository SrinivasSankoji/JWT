package com.chary.bhaumik.jwt.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.chary.bhaumik.jwt.session.RedisSession;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTUtil 
{
	@Value("${security.jwt.token.secret-key}")
	private String secretkey;
	
	@Value("${security.jwt.token.expire-length}")
	private long validityInMilliseconds;
	
	@Autowired
	RedisSession redisSession;

    public String extractSubject(String token) 
    {
        return extractClaim(token, Claims::getSubject);
    }
    
    public boolean validateToken(String token, UserDetails userDetails)
   	{
   		final String username = extractUsername(token);
   		return username.equals(userDetails.getUsername()) && !isTokenExpired(token) ? true : false;
   	}
    
    public String extractUsername(String token) 
    {
    	Claims claims = extractAllClaims(token);
        return (String) claims.get("userName");
    }
    
    public boolean isTokenExpired(String token) 
	{
		if (extractExpiration(token).before(new Date())) {
			return true;
		} else {
			return refreshToken(token);
		}
	}
    
    public boolean refreshToken(String token)
	{
		String userName = extractUsername(token);
		long lastRequest = redisSession.getLastReuestofUser(userName);
		long diff = (new Date(lastRequest).getTime() - extractExpiration(token).getTime()) / (60 * 1000) % 60;
		if (diff < 5) {
			Map<String, UserDetails> map = new HashMap<>();
			map.put(generateToken(redisSession.getUserDetails(userName)),
					redisSession.getUserDetails(extractUsername(token)));
			redisSession.put(
					userName.toUpperCase().concat("|").concat((new Date(System.currentTimeMillis())).toString()), map);
			return true;
		}
		return false;
	}
    
    public Date extractExpiration(String token) 
    {
        return extractClaim(token, Claims::getExpiration);
    }
    
    public String generateToken(UserDetails userDetails) 
	{
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails);
	}
    
    private String createToken(Map<String, Object> claims, UserDetails userDetails) 
	{
		return Jwts.builder().setClaims(claims)
		.claim("userName", userDetails.getUsername())
		.claim("role", userDetails.getAuthorities())
		.setSubject("AcessToken").setIssuedAt(new Date(System.currentTimeMillis()))
		.setExpiration(new Date(System.currentTimeMillis() + validityInMilliseconds))
		.signWith(SignatureAlgorithm.HS256, secretkey).compact();
	}
    
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) 
	{
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
    
    private Claims extractAllClaims(String token) 
    {
        return Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token).getBody();
    }
}
