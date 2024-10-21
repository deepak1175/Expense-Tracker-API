package com.deepak.expense_tracker_api.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.deepak.expense_tracker_api.entity.ErrorObject;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorObject> handleexpensenotfound(ResourceNotFoundException ex,WebRequest request)
	{
		ErrorObject error=new ErrorObject();
		error.setMessage(ex.getMessage());
		error.setStatuscode(HttpStatus.NOT_FOUND.value());
		error.setTimestamp(new Date());
		return new ResponseEntity<ErrorObject>(error,HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorObject> handlemethodargumentmismatchexception(MethodArgumentTypeMismatchException ex,WebRequest request)
	{
		ErrorObject error=new ErrorObject();
		error.setMessage(ex.getMessage());
		error.setStatuscode(HttpStatus.BAD_REQUEST.value());
		error.setTimestamp(new Date());
		return new ResponseEntity<ErrorObject>(error,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorObject> handlegeneralexception(Exception ex,WebRequest request)
	{
		ErrorObject error=new ErrorObject();
		error.setMessage(ex.getMessage());
		error.setStatuscode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setTimestamp(new Date());
		return new ResponseEntity<ErrorObject>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@ExceptionHandler(ItemAlreadyExistsException.class)
	public ResponseEntity<ErrorObject> handlealreadyexistsexception(ItemAlreadyExistsException ex,WebRequest request)
	{
		ErrorObject error=new ErrorObject();
		error.setMessage(ex.getMessage());
		error.setStatuscode(HttpStatus.CONFLICT.value());
		error.setTimestamp(new Date());
		return new ResponseEntity<ErrorObject>(error,HttpStatus.CONFLICT);
		
	}
}
