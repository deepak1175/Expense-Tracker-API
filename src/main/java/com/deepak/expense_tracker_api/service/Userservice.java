package com.deepak.expense_tracker_api.service;

import org.springframework.stereotype.Service;

import com.deepak.expense_tracker_api.entity.User;
import com.deepak.expense_tracker_api.entity.Usermodel;


public interface Userservice {
	User createuser(Usermodel user);
	User read();
	User update(User user);
	void delete();
	
	User getloggedinuser();
}
