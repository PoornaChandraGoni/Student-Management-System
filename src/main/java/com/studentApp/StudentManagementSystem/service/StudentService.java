package com.studentApp.StudentManagementSystem.service;

import com.studentApp.StudentManagementSystem.dto.StudentRequest;
import com.studentApp.StudentManagementSystem.dto.StudentResponse;

public interface StudentService {
	
	public void createStudent(StudentRequest studentRequest);

	public StudentResponse getStudent(Long id);

	public StudentResponse updateStudent(Long id, StudentRequest studentRequest);
	
	
}
