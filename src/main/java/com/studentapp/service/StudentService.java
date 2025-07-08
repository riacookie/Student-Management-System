package com.studentapp.service;

import com.studentapp.exception.DuplicateStudentException;
import com.studentapp.exception.InvalidGPAException;
import com.studentapp.exception.StudentNotFoundException;
import com.studentapp.model.Student;
import java.io.File;
import java.util.List;

public interface StudentService {
	void addStudent(Student student) throws DuplicateStudentException, InvalidGPAException;
	void deleteStudent(int studentId) throws StudentNotFoundException;
	List<Student> searchStudent(String name);
	List<Student> getAllStudents();
	boolean login(String username, String password);
	String generateResetToken();
	File loadStudentFile(String filename);
}