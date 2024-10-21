package com.deepak.expense_tracker_api.entity;

import java.util.Date;

import lombok.Data;


@Data
public class ErrorObject {
	private int statuscode;
	private String message;
	private Date timestamp;
	
}
