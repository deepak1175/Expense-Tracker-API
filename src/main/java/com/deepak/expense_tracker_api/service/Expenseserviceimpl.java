package com.deepak.expense_tracker_api.service;

import java.sql.Date;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.deepak.expense_tracker_api.dto.CategoryDTO;
import com.deepak.expense_tracker_api.dto.ExpenseDTO;
import com.deepak.expense_tracker_api.entity.Categoryentity;
import com.deepak.expense_tracker_api.entity.Expense;
import com.deepak.expense_tracker_api.exceptions.ResourceNotFoundException;
import com.deepak.expense_tracker_api.repository.CategoryRepository;
import com.deepak.expense_tracker_api.repository.ExpenseRepository;


@Service
public class Expenseserviceimpl implements ExpenseService {
	
	@Autowired
	private ExpenseRepository expenserepo;
	
	@Autowired
	private CategoryRepository categoryrepo;
	
	@Autowired
	private Userservice userservice;
	
	@Override
	public List<ExpenseDTO> getallexpenses(Pageable page) 
	{
		// TODO Auto-generated method stub
		
		List<Expense> listofexpenses=expenserepo.findByUserId(userservice.getloggedinuser().getId(),page).toList();
		return listofexpenses.stream().map(expense -> maptoDTO(expense)).collect(Collectors.toList());	
	}

	@Override
	public ExpenseDTO getexpensebyid(String expenseid) {
		// TODO Auto-generated method stub
		Expense existingexpense = getexpensentity(expenseid);
		return maptoDTO(existingexpense);
	}

	private Expense getexpensentity(String expenseid) {
		Optional<Expense> expense=expenserepo.findByUserIdAndExpenseid(userservice.getloggedinuser().getId()
				,expenseid);
		if(!expense.isPresent())
		{	
			throw new ResourceNotFoundException("Expense is not found for the id " + expenseid);
		}
		return expense.get();
	}

	@Override
	public void deletebyid(String id) {
		// TODO Auto-generated method stub
		Expense expense=getexpensentity(id);
		expenserepo.delete(expense);
	}

	@Override
	public ExpenseDTO saveexpensedetails(ExpenseDTO expenseDTO) {
		// TODO Auto-generated method stub
		Optional<Categoryentity> category=categoryrepo.findByUserIdAndCategoryid(userservice.getloggedinuser().getId()
				, expenseDTO.getCategoryid());
		if(!category.isPresent())
		{
			throw new ResourceNotFoundException("Category not found for the id - "+expenseDTO.getCategoryid());
		}
		expenseDTO.setExpenseId(UUID.randomUUID().toString());
		Expense newexpense=maptoEntity(expenseDTO);
		newexpense.setCategory(category.get());
		newexpense.setUser(userservice.getloggedinuser());
		newexpense=expenserepo.save(newexpense);
		return maptoDTO(newexpense);
	}

	private ExpenseDTO maptoDTO(Expense newexpense) {
		// TODO Auto-generated method stub
		return ExpenseDTO.builder()
				.expenseId(newexpense.getExpenseid())
				.name(newexpense.getName())
				.description(newexpense.getDescription())
				.amount(newexpense.getAmount())
				.date(newexpense.getDate())
				.createdAt(newexpense.getCreatedAt())
				.updatedAt(newexpense.getUpdatedAt())
				.categorydto(maptocategoryDTO(newexpense.getCategory())).build();
	}

	private CategoryDTO maptocategoryDTO(Categoryentity category) {
		// TODO Auto-generated method stub
		return CategoryDTO.builder().name(category.getName())
				.categoryid(category.getCategoryid()).build();
	}

	private Expense maptoEntity(ExpenseDTO expenseDTO) {
		// TODO Auto-generated method stub
		return Expense.builder().expenseid(expenseDTO.getExpenseId())
				.name(expenseDTO.getName())
				.description(expenseDTO.getDescription())
				.date(expenseDTO.getDate())
				.amount(expenseDTO.getAmount())
				.build();
	}

	@Override
	public ExpenseDTO updateexpensedetails(String id, ExpenseDTO expensedto) {
		// TODO Auto-generated method stub
		Expense existingexpense=getexpensentity(id);
		if(expensedto.getCategoryid()!=null)
		{
			Optional<Categoryentity> optionalcategory=categoryrepo.findByUserIdAndCategoryid(userservice.getloggedinuser().getId()
					, expensedto.getCategoryid());
			if(!optionalcategory.isPresent())
			{
				throw new ResourceNotFoundException("Category is not found for the id- "+expensedto.getCategoryid());
			}
			existingexpense.setCategory(optionalcategory.get());
			
		}
		existingexpense.setName(expensedto.getName()!=null ? expensedto.getName() : existingexpense.getName());
		existingexpense.setDescription(expensedto.getDescription()!=null ? expensedto.getDescription() : existingexpense.getDescription());
		existingexpense.setDate(expensedto.getDate()!=null ? expensedto.getDate() : existingexpense.getDate());
		existingexpense.setAmount(expensedto.getAmount()!=null ? expensedto.getAmount() : existingexpense.getAmount());
//		existingexpense.setCreatedAt(expense.getCreatedAt()!=null ? expense.getCreatedAt() : existingexpense.getCreatedAt());
//		existingexpense.setUpdatedAt(expense.getUpdatedAt()!=null ? expense.getUpdatedAt() : existingexpense.getUpdatedAt());
		existingexpense=expenserepo.save(existingexpense);
		return maptoDTO(existingexpense);
		
	}

	@Override
	public List<ExpenseDTO> readbycategory(String category, Pageable page) {
		// TODO Auto-generated method stub
		Optional<Categoryentity> optionalcategory=categoryrepo.findByNameAndUserId(category,userservice.getloggedinuser().getId());
		if(!optionalcategory.isPresent())
		{
			throw new ResourceNotFoundException("Category is not found for the name - "+category);
		}
		List<Expense> expenseentity= expenserepo.findByUserIdAndCategoryId(userservice.getloggedinuser().getId(),
				optionalcategory.get().getId(), page).toList();
		return expenseentity.stream().map(entity -> maptoDTO(entity)).collect(Collectors.toList());
	}

	@Override
	public List<ExpenseDTO> readbyname(String name, Pageable page) {
		// TODO Auto-generated method stub
		List<Expense> expenseentity=expenserepo.findByUserIdAndNameContaining(userservice.getloggedinuser().getId(),name, page).toList();
		return expenseentity.stream().map(entity -> maptoDTO(entity)).collect(Collectors.toList());
	}

	@Override
	public List<ExpenseDTO> readbydate(Date startdate, Date enddate, Pageable page) {
		// TODO Auto-generated method stub
		if(startdate==null)
		{
			startdate=new Date(0);
		}
		if(enddate==null)
		{
			enddate=new Date(System.currentTimeMillis());
		}
		List<Expense> expense=expenserepo.findByUserIdAndDateBetween(userservice.getloggedinuser().getId(),startdate,enddate, page).toList();
		return expense.stream().map(entity -> maptoDTO(entity)).collect(Collectors.toList());
	}

	@Override
	public void deletebyuserid() {
		// TODO Auto-generated method stub
		Long id=userservice.getloggedinuser().getId();
		expenserepo.deleteByUserId(id);
	}

}
