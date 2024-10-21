package com.deepak.expense_tracker_api.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.deepak.expense_tracker_api.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userrepo;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		com.deepak.expense_tracker_api.entity.User existinguser=userrepo.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found for the email - "+
		email));
		return new org.springframework.security.core.userdetails.User(existinguser.getEmail(),existinguser.getPassword(),
				new ArrayList<>());
	}

}
