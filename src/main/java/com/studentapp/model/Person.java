package com.studentapp.model;

/**
 * Represents the abstract concept of a Person.
 * This demonstrates ABSTRACTION. This class cannot be instantiated directly
 * and is intended to be extended by more specific classes like Student.
 */
public abstract class Person {
	protected String studentId;
	protected String fullName;
	protected int gpa;

	public Person(String studentId, String fullName) {
		this.studentId = studentId;
		this.fullName = fullName;
	}

	// --- Getters and Setters ---
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
}
