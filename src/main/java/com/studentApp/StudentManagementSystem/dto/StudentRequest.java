package com.studentApp.StudentManagementSystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class StudentRequest {
	
	@NotBlank(message= "Name is required")
	@Size(max=100)
	private String name;
	
	
	@NotBlank(message= "email is required")
	@Email(message="Email must be valid")
	private String email;
	
	public StudentRequest() {}
	
	 public StudentRequest(String name, String email) {
	        this.name = name; this.email = email;
	    }
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

}
