package com.studentApp.StudentManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studentApp.StudentManagementSystem.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

	
	
}
