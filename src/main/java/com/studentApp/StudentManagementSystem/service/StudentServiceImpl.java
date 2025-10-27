package com.studentApp.StudentManagementSystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.studentApp.StudentManagementSystem.dto.StudentRequest;
import com.studentApp.StudentManagementSystem.dto.StudentResponse;
import com.studentApp.StudentManagementSystem.exception.StudentNotFoundException;
import com.studentApp.StudentManagementSystem.model.Student;
import com.studentApp.StudentManagementSystem.repository.StudentRepository;
import com.studentApp.StudentManagementSystem.util.StudentMapper;

@Service
public class StudentServiceImpl implements StudentService {

	private StudentMapper studentMapper;
	private StudentRepository studentRepository;

	@Autowired
	public StudentServiceImpl(StudentMapper studentMapper, StudentRepository studentRepository) {
		this.studentMapper = studentMapper;
		this.studentRepository = studentRepository;
	}

	@Override
	public StudentResponse createStudent(StudentRequest studentRequest) {
		Student entity = studentMapper.toEntity(studentRequest);
		Student saved = studentRepository.save(entity);
		return studentMapper.toResponse(saved);
	}

	// Get student by ID.
	@Override
	public ResponseEntity<StudentResponse> getStudent(Long id) {
		Student studentById = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
		return ResponseEntity.ok(studentMapper.toResponse(studentById));
	}

	@Override
	public StudentResponse updateStudent(Long id, StudentRequest studentRequest) {
		Student existing = studentRepository.findById(id).get();
		existing.setName(studentRequest.getName());
		existing.setEmail(studentRequest.getEmail());
		Student updated = studentRepository.save(existing);
		return studentMapper.toResponse(updated);
	}

	@Override
	public ResponseEntity<List<StudentResponse>> foundStudent(String name, String email, Long id) {
		List<Student> studentList = new ArrayList<>();

		// case:1 - getting student details by id
		if (id != null) {
			studentRepository.findById(id).ifPresent(studentList::add);
		}

		// case:2 - getting student details by name or email
		if ((name != null && !name.isBlank()) || (email != null && !email.isBlank())) {
			List<Student> nameEmailMatches = studentRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
					name == null ? "" : name, email == null ? "" : email

			);
			
			for(Student students :nameEmailMatches) {
				if(!studentList.contains(students)) {
					studentList.add(students);
				}
			}
		}

		// case:3 - nothing provided

//		else {
//			studentList = studentRepository.findAll();
//		}

		// convert entities to response DTO's

		List<StudentResponse> responses = new ArrayList<>();

		for (Student student : studentList) {
			StudentResponse response = studentMapper.toResponse(student);
			responses.add(response);

		}
		
		/*The ResponseEntity constructor takes two things:
		 * new ResponseEntity<>(body, status);
		 * body → the actual content you want to send (your data)
         * status → the HTTP status code (like OK, NOT_FOUND, etc.)
		 */
		
		return new ResponseEntity<List<StudentResponse>>(responses, HttpStatus.OK);

	}

}
