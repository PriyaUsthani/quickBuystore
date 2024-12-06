package com.example.userservice.mainapplication.components;

import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class TokenProvider {
	
	@Value("${jwt.expiration}")
	private long EXPIRATION;
	
	@Value("${jwt.secretkey}")
	private String SECRETKEY;

	
	@SuppressWarnings("deprecation")
	public String generateToken(UserDetails user) {
	try {
		return Jwts.builder()
				.setSubject(user.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+EXPIRATION))
				.compact();
	}
	catch(Exception e) {
		e.printStackTrace();
		System.out.println("Token Creation Failed");
		return null;
	}
	}
	
	public Claims extractAllClaims(String token) {
		Claims claims= Jwts.parser()
				.setSigningKey(SECRETKEY)
				.build()
				.parseClaimsJws(token)
				.getBody();
		
		return claims;
	}
	
	public <T> T extractClaim(String token,Function<Claims,T> resolver) {
		final Claims claim = extractAllClaims(token);
		return resolver.apply(claim);
	}
	
	public String extractUsername(String token) {
		return extractClaim(token,Claims::getSubject);
	}
	public boolean validateToken(String token,UserDetails user) {
		String tokenUsername= extractUsername(token);
		return tokenUsername.equals(user.getUsername());
	} 


}
