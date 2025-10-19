package com.studentApp.StudentManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentApp.StudentManagementSystem.dto.StudentRequest;
import com.studentApp.StudentManagementSystem.dto.StudentResponse;
import com.studentApp.StudentManagementSystem.service.StudentService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class StudentController {
	
	private StudentService studentService;
	
	@Autowired
	public StudentController(StudentService studentService) {
		this.studentService=studentService;
	}
	
	
	@PostMapping("/students")
	public void createStudent(@Valid @RequestBody StudentRequest studentRequest) {
		studentService.createStudent(studentRequest);
		
	}
	
	@GetMapping("/{id}")
	public StudentResponse getStudentBYId(@PathVariable Long id ) {
		return studentService.getStudent(id);
	}
	
}
