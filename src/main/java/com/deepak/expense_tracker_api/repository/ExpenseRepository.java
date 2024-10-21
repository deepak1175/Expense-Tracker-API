package com.deepak.expense_tracker_api.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deepak.expense_tracker_api.dto.ExpenseDTO;
import com.deepak.expense_tracker_api.entity.Expense;
import com.deepak.expense_tracker_api.entity.User;


@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
	Page<Expense> findByUserIdAndCategory(Long id,String category,Pageable page);
	
	Page<Expense> findByUserIdAndCategoryId(Long id,Long categoryid,Pageable page);
	
	
	Page<Expense> findByUserIdAndNameContaining(Long id,String keyword,Pageable page);
	Page<Expense> findByUserIdAndDateBetween(Long id,Date startdate,Date enddate,Pageable page);
	Page<Expense> findByUserId(Long user,Pageable page);
	Optional<Expense> findByUserIdAndExpenseid(Long userid,String expenseid);
	void deleteByUserId(Long userid);
}
