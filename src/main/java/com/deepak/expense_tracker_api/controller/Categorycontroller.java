package com.deepak.expense_tracker_api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.deepak.expense_tracker_api.dto.CategoryDTO;
import com.deepak.expense_tracker_api.io.Categoryrequest;
import com.deepak.expense_tracker_api.io.Categoryresponse;
import com.deepak.expense_tracker_api.service.CategoryService;

import io.jsonwebtoken.lang.Collections;


/**
 * This controller is for managing the categories
 * @author Deepak Bisht
 * */


@RestController
@RequestMapping("/categories")
public class Categorycontroller {
	
	@Autowired
	private CategoryService categoryservice;
	
	/**
	 * API for creating the category
	 * @param Categoryrequest
	 * @return Categoryresponse
	 * */
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("")
	public Categoryresponse createCategory(@RequestBody Categoryrequest request)
	{
		CategoryDTO categoryDTO=mapToDTO(request);
		categoryDTO=categoryservice.savecategory(categoryDTO);
		return maptoResponse(categoryDTO);
	}
	
	/**
	 * API for reading the category
	 * @return List
	 * */
	@GetMapping()
	public List<Categoryresponse> readcategories()
	{
		List<CategoryDTO> list=categoryservice.getallcategories();
		return list.stream().map(categoryDTO->maptoResponse(categoryDTO)).collect(Collectors.toList());
	}
	
	/**
	 * API for deleting the category
	 * @param Categoryid
	 * @return void
	 * */
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{categoryid}")
	public void deletecategory(@PathVariable String categoryid)
	{
		categoryservice.deletecategory(categoryid);
	}

	
	/**
	 * Mapper method for converting categoryDTO to categoryresponse 
	 * @param CategoryDTO
	 * @return Categoryresponse
	 * */
	private Categoryresponse maptoResponse(CategoryDTO categoryDTO) {
		// TODO Auto-generated method stub
		return Categoryresponse.builder().categoryid(categoryDTO.getCategoryid())
		.name(categoryDTO.getName())
		.description(categoryDTO.getDescription())
		.categoryIcon(categoryDTO.getCategoryIcon())
		.createdAt(categoryDTO.getCreatedAt())
		.updatedAt(categoryDTO.getUpdatedAt())
		.build();
	}

	
	/**
	 * Mapper method for converting Categoryrequest to categoryDTO
	 * @param Categoryrequest
	 * @return CategoryDTO
	 * */
	private CategoryDTO mapToDTO(Categoryrequest request) 
	{
		return CategoryDTO.builder().name(request.getName())
		.description(request.getDescription())
		.categoryIcon(request.getIcon())
		.build();
	}

}
