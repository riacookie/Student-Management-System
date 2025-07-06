package com.studentapp.exception;

/**
 * Custom exception for when an attempt is made to add a student
 * with an ID that already exists in the system.
 */
public class DuplicateStudentException extends Exception {
	public DuplicateStudentException(String message) {
		super(message);
	}
}