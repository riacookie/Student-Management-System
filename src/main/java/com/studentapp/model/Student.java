package com.studentapp.model;

/**
 * Represents a Student, extending the Person abstract class.
 * This class demonstrates ENCAPSULATION by keeping its fields private
 * and providing public getter and setter methods for access.
 */
public class Student extends Person {
	protected double gpa;
	protected int listId;

	public Student(String studentId, String fullName, double gpa, int listId ) {
		super(studentId, fullName);
		this.gpa = gpa;
		this.listId = listId;
	}

	// --- Getters and Setters ---
	public double getGpa() {
		return gpa;
	}

	public void setGpa(double gpa) {
		this.gpa = gpa;
	}

	public int getListId() {
		return listId;
	}

	public void setListId(int listId) {
		this.listId = listId;
	}

	@Override
	public String toString() {
		return String.format("| %-10d | %-15s | %-25 | %-4.2f |", listId, studentId, fullName, gpa);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Student student = (Student) obj;
		return Double.compare(student.gpa, gpa) == 0 && listId == student.listId && studentId.equals(student.studentId) && fullName.equals(student.fullName);
	}
}
