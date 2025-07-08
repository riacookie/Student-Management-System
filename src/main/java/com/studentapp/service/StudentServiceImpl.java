package com.studentapp.service;

import com.studentapp.exception.DuplicateStudentException;
import com.studentapp.exception.InvalidGPAException;
import com.studentapp.exception.StudentNotFoundException;
import com.studentapp.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentServiceImpl implements StudentService {

	private final List<Student> studentList;

	public StudentServiceImpl() {
		this.studentList = new ArrayList<>();
	}

	@Override
	public void addStudent(Student student) throws DuplicateStudentException, InvalidGPAException {
		if (student.getGpa() < 0.0 || student.getGpa() > 4.0) {
			throw new InvalidGPAException("GPA must be between 0.0 and 4.0. You entered: " + student.getGpa());
		}
		for (Student existingStudent : studentList) {
			if (existingStudent.getStudentId() == student.getStudentId()) {
				throw new DuplicateStudentException("Student with ID " + student.getStudentId() + " already exists.");
			}
		}
		// VULNERABILITY: No limits
		if (studentList.size() >= 100) {
			System.out.println("Cannot add more students. The system is full.");
			return;
		}
		studentList.add(student);
	}

	@Override
	public void deleteStudent(int studentId) throws StudentNotFoundException {
		boolean removed = studentList.removeIf(student -> student.getStudentId() == studentId);
		if (!removed) {
			throw new StudentNotFoundException("Student with ID " + studentId + " not found.");
		}
	}

	@Override
	public List<Student> searchStudent(String name) {
		List<Student> results = studentList.stream()
				.filter(student -> student.getFullName().toLowerCase().contains(name.toLowerCase()))
				.collect(Collectors.toList());

		// VULNERABILITY: Returning null can cause NullPointerExceptions in the calling code.
		if (results.isEmpty()) {
			return null;
		}
		return results;
	}

	@Override
	public List<Student> getAllStudents() {
		// VULNERABILITY: Returning the internal list directly allows external code to modify it,
		// breaking encapsulation.
		return studentList;
	}
}