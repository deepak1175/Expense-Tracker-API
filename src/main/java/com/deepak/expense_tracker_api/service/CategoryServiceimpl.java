package com.deepak.expense_tracker_api.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deepak.expense_tracker_api.dto.CategoryDTO;
import com.deepak.expense_tracker_api.dto.UserDTO;
import com.deepak.expense_tracker_api.entity.Categoryentity;
import com.deepak.expense_tracker_api.entity.User;
import com.deepak.expense_tracker_api.exceptions.ItemAlreadyExistsException;
import com.deepak.expense_tracker_api.exceptions.ResourceNotFoundException;
import com.deepak.expense_tracker_api.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;


@Service
public class CategoryServiceimpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryrepo;
	
	@Autowired
	private Userservice userservice;
	
	
	/**
	 * This  is  for reading the categories from database
	 * @return List
	 * */
	@Override
	public List<CategoryDTO> getallcategories() {
		List<Categoryentity> list=categoryrepo.findByUserId(userservice.getloggedinuser().getId());
		// TODO Auto-generated method stub
		return list.stream().map(categoryentity -> maptoDTO(categoryentity)).collect(Collectors.toList());
	}

	
	/**
	 * Mapper method to convert categoryentity to categoryDTO
	 * @param categoryentity
	 * @return CategoryDTO
	 * */
	private CategoryDTO maptoDTO(Categoryentity categoryentity) {
		// TODO Auto-generated method stub
		return CategoryDTO.builder().categoryid(categoryentity.getCategoryid())
		.categoryIcon(categoryentity.getCategoryicon())
		.description(categoryentity.getDescription())
		.name(categoryentity.getName())
		.createdAt(categoryentity.getCreatedAt())
		.updatedAt(categoryentity.getUpdatedAt())
		.user(maptouserDTO(categoryentity.getUser())).build();
	}

	
	/**
	 * Mapper method to convert user to userDTO
	 * @param user
	 * @return userDTO
	 * */
	private UserDTO maptouserDTO(User user) {
		// TODO Auto-generated method stub
		return UserDTO.builder().email(user.getEmail()).name(user.getName()).build();
	}

	/**
	 * This  is  for creating new categories 
	 * @param CategoryDTO
	 * @return CategoryDTO
	 * */
	@Override
	public CategoryDTO savecategory(CategoryDTO categorydto) {
		
		// TODO Auto-generated method stub
		
		Boolean iscategory=categoryrepo.existsByNameAndUserId(categorydto.getName()
				,userservice.getloggedinuser().getId());
		if(iscategory)
		{
			throw new ItemAlreadyExistsException("Category is present for the name - " + categorydto.getName());
		}
		Categoryentity categoryentity=maptoentity(categorydto);
		categoryentity=categoryrepo.save(categoryentity);
		return maptoDTO(categoryentity);
	}

	
	/**
	 * Mapper method to convert categoryDTO to categoryentity
	 * @param categorydto
	 * @return Categoryentity
	 * */
	private Categoryentity maptoentity(CategoryDTO categorydto) {
		// TODO Auto-generated method stub
		return  Categoryentity.builder().name(categorydto.getName())
				.description(categorydto.getDescription())
				.categoryicon(categorydto.getCategoryIcon())
				.categoryid(UUID.randomUUID().toString())
				.user(userservice.getloggedinuser())
				.build();
	}

	
	/**
	 * This  is  for delete the categories 
	 * @param categoryid
	 * @return void
	 * */
	@Override
	public void deletecategory(String categoryid) {
		// TODO Auto-generated method stub
		Optional<Categoryentity> category=categoryrepo.findByUserIdAndCategoryid(userservice.getloggedinuser().getId(), categoryid);
		if(!category.isPresent())
		{
			throw new ResourceNotFoundException("Category is not found for the id - "+categoryid);
		}
		categoryrepo.delete(category.get());
	}

}
