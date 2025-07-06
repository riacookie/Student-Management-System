package com.studentapp.service;

import com.studentapp.exception.DuplicateStudentException;
import com.studentapp.exception.InvalidGPAException;
import com.studentapp.exception.StudentNotFoundException;
import com.studentapp.model.Student;

import java.util.List;

/**
 * This is an INTERFACE. It defines a "contract" for student management operations.
 * It specifies WHAT methods a service class must have, but not HOW they are implemented.
 * This promotes loose coupling, as the UI will depend on this interface, not the concrete implementation.
 */
public interface StudentService {

	/**
	 * Adds a new student to the system.
	 * @param student The student object to add.
	 * @throws DuplicateStudentException if a student with the same ID already exists.
	 * @throws InvalidGPAException if the student's GPA is not between 0.0 and 4.0.
	 */
	void addStudent(Student student) throws DuplicateStudentException, InvalidGPAException;

	/**
	 * Deletes a student from the system by their ID.
	 * @param studentId The ID of the student to delete.
	 * @throws StudentNotFoundException if no student with the given ID is found.
	 */
	void deleteStudent(int studentId) throws StudentNotFoundException;

	/**
	 * Searches for students by full or partial name, case-insensitively.
	 * @param name The search query for the student's name.
	 * @return A list of students matching the search criteria.
	 */
	List<Student> searchStudent(String name);

	/**
	 * Retrieves all students currently in the system.
	 * @return A list of all students.
	 */
	List<Student> getAllStudents();
}
