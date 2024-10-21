package com.deepak.expense_tracker_api.service;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.deepak.expense_tracker_api.dto.ExpenseDTO;
import com.deepak.expense_tracker_api.entity.Expense;

public interface ExpenseService {
	List<ExpenseDTO> getallexpenses(Pageable page);
	ExpenseDTO getexpensebyid(String id);
	void deletebyid(String id);
	ExpenseDTO saveexpensedetails(ExpenseDTO expensedto);
	ExpenseDTO updateexpensedetails(String id,ExpenseDTO expense);
	List<ExpenseDTO> readbycategory(String category,Pageable page);
	List<ExpenseDTO> readbyname(String name,Pageable page);
	List<ExpenseDTO> readbydate(Date startdate,Date enddate,Pageable page);
	void deletebyuserid();
}
