package com.studentApp.StudentManagementSystem.exception;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.servlet.http.HttpServletRequest;


@RestControllerAdvice
public class GlobalExceptionHandler {
	private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	 private Map<String, Object> baseBody(HttpStatus status, String error, String message, HttpServletRequest request) {
		 Map<String, Object> baseBodyMessages = new HashMap<>();
		 baseBodyMessages.put("timestamp", OffsetDateTime.now().toString());
		 baseBodyMessages.put("status", status.value());
		 baseBodyMessages.put("error", error);
		 baseBodyMessages.put("message", message);
		 baseBodyMessages.put("path", request.getRequestURI());
		 return baseBodyMessages;
		 
	 }
	  
	 @ExceptionHandler(MethodArgumentTypeMismatchException.class)
	 public ResponseEntity<Map<String, Object>>  handleTypeMismatch (MethodArgumentTypeMismatchException typeMismatchException, HttpServletRequest request) {
		String message =  String.format("Invalid value '%s' for parameter '%s' Expected type is %s.",
		typeMismatchException.getValue(), typeMismatchException.getName(), typeMismatchException.getRequiredType().getSimpleName());
		
		Map<String, Object> base = baseBody(HttpStatus.BAD_REQUEST, "Invalid parametr type", "invalid number", request);
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(base);
	 }
	
	
}
