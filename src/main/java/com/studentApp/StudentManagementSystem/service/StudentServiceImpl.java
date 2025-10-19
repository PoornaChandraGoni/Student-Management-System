package com.studentApp.StudentManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentApp.StudentManagementSystem.dto.StudentRequest;
import com.studentApp.StudentManagementSystem.dto.StudentResponse;
import com.studentApp.StudentManagementSystem.model.Student;
import com.studentApp.StudentManagementSystem.repository.StudentRepository;
import com.studentApp.StudentManagementSystem.util.StudentMapper;

@Service
public class StudentServiceImpl implements StudentService{
	
	private StudentMapper studentMapper;
	private StudentRepository studentRepository;
	
	@Autowired
	public StudentServiceImpl(StudentMapper studentMapper, StudentRepository studentRepository) {
		this.studentMapper=studentMapper;
		this.studentRepository=studentRepository;
	}
	
	@Override
	public void createStudent(StudentRequest studentRequest) {
		Student entity = studentMapper.toEntity(studentRequest);
		Student saved = studentRepository.save(entity);
		StudentResponse studentResponse = studentMapper.toResponse(saved);
	}

	@Override
	public StudentResponse getStudent(Long id) {
		Student studentById = studentRepository.findById(id).get();
		return studentMapper.toResponse(studentById);
	}

	@Override
	public StudentResponse updateStudent(Long id, StudentRequest studentRequest) {
	     Student existing  = studentRepository.findById(id).get();
	     existing.setName(studentRequest.getName());
	     existing.setEmai(studentRequest.getEmail());
	     Student updated = studentRepository.save(existing);
	     return studentMapper.toResponse(updated);
	}
	
	
	


}
