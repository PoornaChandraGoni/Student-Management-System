package com.studentApp.StudentManagementSystem.dto;

public class StudentResponse {

	private Long id;
	private String name;
	private String email;

	public StudentResponse() {
	};

	public StudentResponse(Long id, String name, String emai) {
		this.id = id;
		this.name = name;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
