package com.deepak.expense_tracker_api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.deepak.expense_tracker_api.entity.User;
import com.deepak.expense_tracker_api.entity.Usermodel;
import com.deepak.expense_tracker_api.exceptions.ItemAlreadyExistsException;
import com.deepak.expense_tracker_api.exceptions.ResourceNotFoundException;
import com.deepak.expense_tracker_api.repository.UserRepository;


@Service
public class Userserviceimpl implements Userservice {

	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Autowired
	UserRepository userrepo;
	
	
	
	
	@Override
	public User createuser(Usermodel umodel) {
		// TODO Auto-generated method stub
		if(userrepo.existsByEmail(umodel.getEmail()))
		{
			throw new ItemAlreadyExistsException("User is already registered with email - "+umodel.getEmail());
		}
		User user=new User();
		BeanUtils.copyProperties(umodel, user);
		user.setPassword(bcryptEncoder.encode(user.getPassword()));
		return userrepo.save(user);
	}


	@Override
	public User read() {
		Long id=getloggedinuser().getId();
		return userrepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User is not found for id - "+id));
	}


	@Override
	public User update(User user) {
		// TODO Auto-generated method stub
//		Long id=userservice.getloggedinuser().getId();
		User existinguser=read();
		existinguser.setName((user.getName()!=null)?user.getName():existinguser.getName());
		existinguser.setEmail((user.getEmail()!=null)?user.getEmail():existinguser.getEmail());
		existinguser.setPassword((user.getPassword()!=null)?bcryptEncoder.encode(user.getPassword()):existinguser.getPassword());
		existinguser.setAge((user.getAge()!=null)?user.getAge():existinguser.getAge());
		return userrepo.save(existinguser);
	}


	@Override
	public void delete() {
		// TODO Auto-generated method stub
		User user=read();

		userrepo.delete(user);
		
	}


	@Override
	public User getloggedinuser() {
		// TODO Auto-generated method stub
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		String email=authentication.getName();
		return userrepo.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User is not found for email - "
				+ ""+email));
	}

}
