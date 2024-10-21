package com.deepak.expense_tracker_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deepak.expense_tracker_api.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User,Long>{
	Boolean existsByEmail(String email);
	Optional<User> findByEmail(String email);
	
}
