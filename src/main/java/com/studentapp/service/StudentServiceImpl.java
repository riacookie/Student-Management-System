package com.studentapp.service;

import com.studentapp.exception.DuplicateStudentException;
import com.studentapp.exception.InvalidGPAException;
import com.studentapp.exception.StudentNotFoundException;
import com.studentapp.model.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The concrete IMPLEMENTATION of the StudentService interface.
 * This class contains the actual business logic for managing students.
 */
public class StudentServiceImpl implements StudentService {
	private final List<Student> studentList;
	private static final int MAX_STUDENTS = 100;

	public StudentServiceImpl() {
		this.studentList = new ArrayList<>();
	}

	@Override
	public void addStudent(Student student) throws DuplicateStudentException, InvalidGPAException {
		// validate GPA
		if (student.getGpa() < 0.0 || student.getGpa() > 4.0) {
			throw new InvalidGPAException("GPA must be between 0.0 and 4.0.");
		}

		// check for duplication
		for (Student existingStudent : studentList) {
			if (existingStudent.equals(student)) {
				throw new DuplicateStudentException("Student with ID " + student.getStudentId() + " already exists.");
			}
		}

		// add the student to the list
		studentList.add(student);
	}

	@Override
	public void deleteStudent(int studentListId) throws StudentNotFoundException {
		for (Student student : studentList) {
			if (student.getListId() == studentListId) {
				studentList.remove(student);
				return;
			}
		}
		// if not found
		throw new StudentNotFoundException("Student with ID " + studentListId + " not found.");
	}

	@Override
	public List<Student> searchStudent(String name) {
		return studentList.stream()
				.filter(student -> student.getFullName().toLowerCase().contains(name.toLowerCase()))
				.collect(Collectors.toList());
	}

	@Override
	public List<Student> getAllStudents() {
		return new ArrayList<>(studentList);
	}
}
