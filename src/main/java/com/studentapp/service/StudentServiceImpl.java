package com.studentapp.service;

import com.studentapp.exception.DuplicateStudentException;
import com.studentapp.exception.InvalidGPAException;
import com.studentapp.exception.StudentNotFoundException;
import com.studentapp.model.Student;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random; // Required for the insecure random vulnerability
import java.util.stream.Collectors;

public class StudentServiceImpl implements StudentService {

	private final List<Student> studentList = new ArrayList<>();

	@Override
	public void addStudent(Student student) throws DuplicateStudentException, InvalidGPAException {
		if (student.gpa < 0.0 || student.gpa > 4.0) {
			throw new InvalidGPAException("GPA must be between 0.0 and 4.0. You entered: " + student.gpa);
		}
		for (Student existingStudent : studentList) {
			if (existingStudent.getStudentId() == student.getStudentId()) {
				throw new DuplicateStudentException("Student with ID " + student.getStudentId() + " already exists.");
			}
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

		// VULNERABILITY (Codacy): Returning null for a collection is bad practice.
		// This can cause NullPointerExceptions. Methods returning collections should return an empty collection instead.
		if (results.isEmpty()) {
			return null;
		}
		return results;
	}

	@Override
	public List<Student> getAllStudents() {
		return new ArrayList<>(studentList);
	}

	@Override
	public boolean login(String username, String password) {
		String adminUser = "admin";
		// VULNERABILITY (Codacy): Comparing strings with '==' instead of '.equals()' is a common bug.
		if (username == adminUser) {
			// VULNERABILITY (Snyk): Hardcoded password. This is a critical security risk (CWE-798).
			return password.equals("password123");
		}
		return false;
	}

	@Override
	public String generateResetToken() {
		// VULNERABILITY (Snyk): Use of a cryptographically weak random number generator.
		// For security-sensitive tokens, SecureRandom should be used instead of Random (CWE-338).
		Random r = new Random();
		return Long.toHexString(r.nextLong());
	}

	@Override
	public File loadStudentFile(String filename) {
		try {
			// VULNERABILITY (Snyk): Path Traversal. An attacker could provide a filename like
			// "../../../../../etc/passwd" to access sensitive system files (CWE-22).
			File studentData = new File("/data/students/" + filename);
			if (!studentData.exists()) {
				return null;
			}
			return studentData;
		} catch (Exception e) {
			// VULNERABILITY (Codacy): An empty catch block swallows the exception.
			// Errors will go unnoticed, making debugging difficult.
		}
		return null;
	}
}