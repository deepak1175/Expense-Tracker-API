package com.deepak.expense_tracker_api.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.deepak.expense_tracker_api.util.jwtTokenUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Jwtrequestfilter extends OncePerRequestFilter{
	
	@Autowired
	private jwtTokenUtil jwttokenutil;

	@Autowired
	private CustomUserDetailsService userdetailsservice;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		final String reuqestTokenHeader=request.getHeader("Authorization");
		String jwttoken=null;
		String username=null;
		if(reuqestTokenHeader!=null && reuqestTokenHeader.startsWith("Bearer "))
		{
			jwttoken=reuqestTokenHeader.substring(7);
			try
			{
				username=jwttokenutil.getusernamefromtoken(jwttoken);
			}
			catch(IllegalArgumentException e)
			{
				throw new RuntimeException("Unable to get the JWT Token");
			}
			catch(ExpiredJwtException e)
			{
				throw new RuntimeException("JWT Token is expired");
			}
		}
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			UserDetails userdetails=userdetailsservice.loadUserByUsername(username);
			if(jwttokenutil.validateToken(jwttoken, userdetails))
			{
				UsernamePasswordAuthenticationToken authtoken=new UsernamePasswordAuthenticationToken
						(userdetails,null,userdetails.getAuthorities());
				authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authtoken);
				
			}
		}
		filterChain.doFilter(request, response);
	}
	
}
