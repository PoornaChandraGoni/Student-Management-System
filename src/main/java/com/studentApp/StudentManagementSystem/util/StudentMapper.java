package com.studentApp.StudentManagementSystem.util;

import org.springframework.stereotype.Component;

import com.studentApp.StudentManagementSystem.dto.StudentRequest;
import com.studentApp.StudentManagementSystem.dto.StudentResponse;
import com.studentApp.StudentManagementSystem.model.Student;

@Component
public class StudentMapper {
	
	public Student toEntity(StudentRequest studentRequest) {
		if(studentRequest == null) return null;
		
		Student student = new Student();
		
		student.setName(studentRequest.getName());
		student.setEmai(studentRequest.getEmail());
		return student;
	}

	public StudentResponse toResponse(Student saved) {
		
		if(saved == null) return null;
		return new StudentResponse(saved.getId(),saved.getName(),saved.getEmai());
		
	}
	

}
