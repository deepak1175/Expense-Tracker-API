package com.deepak.expense_tracker_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.deepak.expense_tracker_api.entity.Authmodel;
import com.deepak.expense_tracker_api.entity.Jwtresponse;
import com.deepak.expense_tracker_api.entity.User;
import com.deepak.expense_tracker_api.entity.Usermodel;
import com.deepak.expense_tracker_api.security.CustomUserDetailsService;
import com.deepak.expense_tracker_api.service.Userserviceimpl;
import com.deepak.expense_tracker_api.util.jwtTokenUtil;

import jakarta.validation.Valid;

@RestController
public class Authcontroller {
	
	@Autowired
	private Userserviceimpl userservice;
	
	@Autowired
	private AuthenticationManager authenticationmanager;
	
	@Autowired
	private CustomUserDetailsService userdetailservice;

	@Autowired
	private jwtTokenUtil jwtTokenUtil;
	
	@PostMapping("/login")
	public ResponseEntity<Jwtresponse> login(@RequestBody Authmodel authmodel) throws Exception
	{		
		authenticate(authmodel.getEmail(),authmodel.getPassword());
		final UserDetails userdetail=userdetailservice.loadUserByUsername(authmodel.getEmail());
		String jwttoken=jwtTokenUtil.generate(userdetail);
		
		return new ResponseEntity<Jwtresponse>(new Jwtresponse(jwttoken),HttpStatus.OK);
	}
	
	
	private void authenticate(String email, String password) throws Exception {
		// TODO Auto-generated method stub
		
		try
		{
			authenticationmanager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
		}
		catch(DisabledException e)
		{
			throw new Exception("User disabled");
		}
		catch(BadCredentialsException e)
		{
			throw new Exception("Bad credentails");
		}
		
		
	}


	@PostMapping("/register")
	public ResponseEntity<User> save(@Valid @RequestBody Usermodel user)
	{
		return new ResponseEntity<User>(userservice.createuser(user),HttpStatus.CREATED);
	}
	
	
}
