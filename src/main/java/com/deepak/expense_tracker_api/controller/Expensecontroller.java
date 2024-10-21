package com.deepak.expense_tracker_api.controller;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.deepak.expense_tracker_api.service.Expenseserviceimpl;
import com.deepak.expense_tracker_api.dto.CategoryDTO;
import com.deepak.expense_tracker_api.dto.ExpenseDTO;
import com.deepak.expense_tracker_api.entity.Expense;
import com.deepak.expense_tracker_api.io.Categoryresponse;
import com.deepak.expense_tracker_api.io.Expenserequest;
import com.deepak.expense_tracker_api.io.Expenseresponse;
import com.deepak.expense_tracker_api.repository.ExpenseRepository;


@RestController
public class Expensecontroller {
		
	@Autowired
	Expenseserviceimpl expenseservice;
	
	@GetMapping("/expenses")
	public List<Expenseresponse> getallexpenses(Pageable page)
	{
		List<ExpenseDTO> listofexpensedto=expenseservice.getallexpenses(page);
		return listofexpensedto.stream().map(expense -> maptoresponse(expense)).collect(Collectors.toList());
	}
	
	@GetMapping("/expenses/{expenseid}")
	public Expenseresponse getexpensebyid(@PathVariable String expenseid)
	{
		ExpenseDTO expensedto= expenseservice.getexpensebyid(expenseid);
		return maptoresponse(expensedto);
	}
	
	
	@ResponseStatus(value=HttpStatus.NO_CONTENT)
	@DeleteMapping("/expenses")
	public void deleteexpensebyid(@RequestParam String expenseid)
	{
		expenseservice.deletebyid(expenseid);
	}
	
	
	
	@PostMapping("/expenses")
	@ResponseStatus(value=HttpStatus.CREATED)
	public Expenseresponse saveexpensedetails(@RequestBody Expenserequest expenserequest)
	{	
		ExpenseDTO expenseDTO=maptoDTO(expenserequest);
		expenseDTO = expenseservice.saveexpensedetails(expenseDTO);
		return maptoresponse(expenseDTO);
	}
	
	
	private Expenseresponse maptoresponse(ExpenseDTO expenseDTO) {
		// TODO Auto-generated method stub
		return Expenseresponse.builder().expenseId(expenseDTO.getExpenseId()).name(expenseDTO.getName())
		.amount(expenseDTO.getAmount())
		.description(expenseDTO.getDescription())
		.date(expenseDTO.getDate())
		.category(maptocategoryresponse(expenseDTO.getCategorydto()))
		.createdAt(expenseDTO.getCreatedAt())
		.updatedAt(expenseDTO.getUpdatedAt())
		.build();
	}

	private Categoryresponse maptocategoryresponse(CategoryDTO categorydto) {
		// TODO Auto-generated method stub
		return Categoryresponse.builder().categoryid(categorydto.getCategoryid())
		.name(categorydto.getName()).build();
	}

	private ExpenseDTO maptoDTO(Expenserequest expenserequest) {
		// TODO Auto-generated method stub
		return ExpenseDTO.builder().name(expenserequest.getName())
		.description(expenserequest.getDescription())
		.amount(expenserequest.getAmount())
		.date(expenserequest.getDate())
		.categoryid(expenserequest.getCategoryId()).build();
	}

	@PutMapping("/expenses/{expenseid}")
	public Expenseresponse updatexpensedetails(@RequestBody Expenserequest expenserequest,@PathVariable String expenseid)
	{	
		ExpenseDTO updatedexpensedto=maptoDTO(expenserequest);
		updatedexpensedto = expenseservice.updateexpensedetails(expenseid, updatedexpensedto);
		return maptoresponse(updatedexpensedto);
	}
	
	@GetMapping("/expenses/category")
	public List<Expenseresponse> getexpensebycategory(@RequestParam String category,Pageable page)
	{
		List<ExpenseDTO> expense= expenseservice.readbycategory(category, page);
		return expense.stream().map(expensedto -> maptoresponse(expensedto)).collect(Collectors.toList());
	}
	
	@GetMapping("/expenses/name")
	public List<Expenseresponse> getexpensebyname(@RequestParam String name,Pageable page)
	{
		List<ExpenseDTO> expensedto=expenseservice.readbyname(name, page);
		return expensedto.stream().map(expense -> maptoresponse(expense)).collect(Collectors.toList());
	}
	
	@GetMapping("/expenses/date")
	public List<Expenseresponse> getexpensebydate(@RequestParam(required=false) Date startdate
			,@RequestParam(required=false) Date enddate
			,Pageable page)
	{
		List<ExpenseDTO> expensedto=expenseservice.readbydate(startdate,enddate, page);
		return expensedto.stream().map(expense -> maptoresponse(expense)).collect(Collectors.toList());
	}
}
