package com.deepak.expense_tracker_api.dto;

import java.sql.Timestamp;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CategoryDTO {
	
	private String categoryid;
	private String name;
	private String description ;
	private String categoryIcon;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	private UserDTO user;
}
