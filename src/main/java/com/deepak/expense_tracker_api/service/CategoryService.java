package com.deepak.expense_tracker_api.service;

import java.util.List;

import com.deepak.expense_tracker_api.dto.CategoryDTO;


/**
 * Service interface for managing the categories
 * @author Deepak Bisht
 * */
public interface CategoryService {
	
	/**
	 * This  is  for reading the categories from database
	 * @return List
	 * */
	List<CategoryDTO> getallcategories();
	
	
	/**
	 * This  is  for creating new categories 
	 * @param CategoryDTO
	 * @return CategoryDTO
	 * */
	CategoryDTO savecategory(CategoryDTO categorydto);
	
	
	/**
	 * This  is  for delete the categories 
	 * @param categoryid
	 * @return void
	 * */
	void deletecategory(String categoryid);
}
