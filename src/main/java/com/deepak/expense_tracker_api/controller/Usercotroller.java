package com.deepak.expense_tracker_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.deepak.expense_tracker_api.entity.User;
import com.deepak.expense_tracker_api.entity.Usermodel;
import com.deepak.expense_tracker_api.service.Userservice;
import com.deepak.expense_tracker_api.service.Userserviceimpl;

import jakarta.validation.Valid;

@RestController
public class Usercotroller {
	
	@Autowired
	private Userserviceimpl userservice;
	
	
	@GetMapping("/profile")
	public ResponseEntity<User> getuserbyid()
	{
		return new ResponseEntity<User>(userservice.read(),HttpStatus.OK);
	}
	
	@PutMapping("/profile")
	public ResponseEntity<User> updateuserbyid(@RequestBody User user)
	{
		User muser=userservice.update(user);
		return new ResponseEntity<User>(muser,HttpStatus.OK);
	}
	
	@DeleteMapping("/deactivate")
	public ResponseEntity<HttpStatus> deleteuserbyid()
	{
		userservice.delete();
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}
}
