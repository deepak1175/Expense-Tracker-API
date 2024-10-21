package com.deepak.expense_tracker_api.entity;

import java.sql.Timestamp;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import com.deepak.expense_tracker_api.dto.CategoryDTO;
import com.deepak.expense_tracker_api.dto.UserDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="tbl_categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Categoryentity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true)
	private String categoryid;
	
	@Column(unique=true)
	private String  name;
	
	
	private String description;
	
	private String categoryicon;
	
	@CreationTimestamp
	private Timestamp createdAt;
	
	@UpdateTimestamp
	private Timestamp updatedAt;
	
	@ManyToOne(fetch=FetchType.LAZY,optional=false)
	@JoinColumn(name="user_id",nullable=false)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private User user;
}
