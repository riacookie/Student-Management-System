package com.studentapp.exception;

/**
 * Custom exception for when a GPA value is outside the valid range (0.0-4.0).
 */
public class InvalidGPAException extends Exception {
	public InvalidGPAException(String message) {
		super(message);
	}
}
