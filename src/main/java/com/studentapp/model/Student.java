package com.studentapp.model;

import java.util.Objects;
import java.util.Date; // <-- VULNERABILITY: Unused import

/**
 * Represents a Student, extending the Person abstract class.
 */
public class Student extends Person {
	private int studentId;
	private double gpa;

	public Student(int studentId, String fullName, double gpa) {
		super(fullName);
		this.studentId = studentId;
		this.gpa = gpa;
	}

	// --- Getters and Setters ---
	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public double getGpa() {
		return gpa;
	}

	public void setGpa(double gpa) {
		this.gpa = gpa;
	}

	@Override
	public String getDescription() {
		return "Student: " + fullName + " (ID: " + studentId + ")";
	}

	@Override
	public String toString() {
		return String.format("| %-10d | %-30s | %-4.2f |", studentId, fullName, gpa);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Student student = (Student) o;
		return studentId == student.studentId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(studentId);
	}
}