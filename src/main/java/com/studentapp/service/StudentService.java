package com.studentapp.service;

import com.studentapp.exception.DuplicateStudentException;
import com.studentapp.exception.InvalidGPAException;
import com.studentapp.exception.StudentNotFoundException;
import com.studentapp.model.Student;
import java.util.List;

// Interface remains the same, as it's the contract
public interface StudentService {
	void addStudent(Student student) throws DuplicateStudentException, InvalidGPAException;
	void deleteStudent(int studentId) throws StudentNotFoundException;
	List<Student> searchStudent(String name);
	List<Student> getAllStudents();
}