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

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) 
    {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) 
	{
	Map<String, Object> claims = new HashMap<>();
	String userName = userDetails.getUsername();
	return createToken(claims, userName);
	}

    private String createToken(Map<String, Object> claims, String userName) 
    {
        return Jwts.builder()
        .setClaims(claims).setSubject(userName)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + validityInMilliseconds))
        .signWith(SignatureAlgorithm.HS256, secretkey).compact();
    }

    public boolean validateToken(String token, UserDetails userDetails)
	{
		final String username = extractUsername(token);
		if((username.equals(userDetails.getUsername()) && !isTokenExpired(token))) {
			return true;
		} else {
			return false;
		}
	}

}
