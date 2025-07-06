package com.studentapp.exception;

/**
 * Custom exception for when a search for a student by ID yields no results.
 */
public class StudentNotFoundException extends Exception {
	public StudentNotFoundException(String message) {
		super(message);
	}
}
