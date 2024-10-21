package com.deepak.expense_tracker_api.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class jwtTokenUtil {

	@Value("${jwt.secret}") 
	private String secret;
	
	public String generate(UserDetails userdetail) {
		
		final long JWT_TOKEN_VALIDITY=5*60*60;
		
		
		// TODO Auto-generated method stub
		Map<String,Object> hm=new HashMap<>();
		
		
		return Jwts.builder().setClaims(hm).setSubject(userdetail.getUsername()).
		setIssuedAt(new Date(System.currentTimeMillis()))
		.setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY*1000))
		.signWith(SignatureAlgorithm.HS512,secret).compact();
	}
	
	public String getusernamefromtoken(String token)
	{
		return getclaimfromtoken(token,Claims::getSubject);
	}
	
	
	private <T> T getclaimfromtoken(String token,Function<Claims,T> claimsresolver)
	{
		final Claims claims=  Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token).getBody();
		return claimsresolver.apply(claims);
	}
	
	public boolean validateToken(String jwtToken,UserDetails userdetails)
	{
		final String username=getusernamefromtoken(jwtToken);
		return username.equals(userdetails.getUsername()) && !isTokenExpired(jwtToken);
	}

	private boolean isTokenExpired(String jwtToken) {
		// TODO Auto-generated method stub
		final Date expiration=getExpirationDatefromToken(jwtToken);
		return expiration.before(new Date());
	}

	private java.util.Date getExpirationDatefromToken(String jwtToken) {
		// TODO Auto-generated method stub
		return getclaimfromtoken(jwtToken,Claims::getExpiration);
	}
}
