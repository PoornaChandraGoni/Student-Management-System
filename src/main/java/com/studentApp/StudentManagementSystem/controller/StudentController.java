package com.studentApp.StudentManagementSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		this.studentService = studentService;
	}

	@PostMapping("/students")
	public ResponseEntity<StudentResponse> createStudent(@Valid @RequestBody StudentRequest studentRequest) {
		StudentResponse response = studentService.createStudent(studentRequest);
		return ResponseEntity.ok(response);

	}

//	@GetMapping("/{id}")
//	public ResponseEntity<StudentResponse> getStudentBYId(@PathVariable Long id) {
//		return studentService.getStudent(id);
//	}

//	@PutMapping("/{id}")
//	public void update(@PathVariable Long id, @RequestBody StudentRequest studentRequest) {
//		studentService.updateStudent(id, studentRequest);
//	}

	@DeleteMapping("/{id}")
	public void deleteStudent(@PathVariable Long id, StudentRequest studentRequest) {

	}

	@GetMapping("/nameOrEmailOrId")
	public ResponseEntity<List<StudentResponse>> findStudent(@RequestParam(required = false) String name,
			@RequestParam(required = false) String email, @RequestParam(required=false) Long id) {
		return studentService.foundStudent(name, email,id);

	}

}
