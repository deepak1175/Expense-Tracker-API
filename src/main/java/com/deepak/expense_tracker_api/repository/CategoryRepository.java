package com.deepak.expense_tracker_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deepak.expense_tracker_api.entity.Categoryentity;

/**
 * JPA repository for Category
 * @author Deepak Bisht
 * */

@Repository
public interface CategoryRepository extends JpaRepository<Categoryentity,Long> {
	
	/**
	 * Finder method to retrieve the category by userid
	 * @param userid
	 * @return list
	 * */
	List<Categoryentity> findByUserId(Long userid);
	
	
	/**
	 * Finder method to retrieve the category by userid and categoryid
	 * @param userid , categoryid
	 * @return optional<categoryentity>
	 * */
	Optional<Categoryentity> findByUserIdAndCategoryid(Long userid,String categoryid);
	
	
	/**
	 * Checks whether category is present or not  by name and userid
	 * @param name , userid
	 * @return boolean
	 * */
	Boolean existsByNameAndUserId(String name,Long userid);
	
	
	/**
	 * Rerieves the category by name and userid
	 * @param  , userid
	 * @return optional<categoryentity>
	 * */
	Optional<Categoryentity> findByNameAndUserId(String name,Long userid);
}
