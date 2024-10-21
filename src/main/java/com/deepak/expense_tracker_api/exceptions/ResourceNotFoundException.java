package com.deepak.expense_tracker_api.exceptions;

public class ResourceNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException(String message)
	{
		super(message);
	}
	
}
