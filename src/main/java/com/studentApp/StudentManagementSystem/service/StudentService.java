package com.studentApp.StudentManagementSystem.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.studentApp.StudentManagementSystem.dto.StudentRequest;
import com.studentApp.StudentManagementSystem.dto.StudentResponse;

public interface StudentService {
	
	public StudentResponse createStudent(StudentRequest studentRequest);

	public ResponseEntity<StudentResponse> getStudent(Long id);

	public StudentResponse updateStudent(Long id, StudentRequest studentRequest);

	public ResponseEntity<List<StudentResponse>> foundStudent(String name, String email, Long id);

	
	
	
}
