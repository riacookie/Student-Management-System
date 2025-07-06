package com.studentapp.ui;

import com.studentapp.exception.DuplicateStudentException;
import com.studentapp.exception.InvalidGPAException;
import com.studentapp.exception.StudentNotFoundException;
import com.studentapp.model.Student;
import com.studentapp.service.StudentService;
import com.studentapp.service.StudentServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * The main entry point for the application.
 * This class is responsible for the User Interface (UI) and user interaction.
 */
public class App {
	// The UI layer depends on the service INTERFACE, not the implementation.
	private static final StudentService studentService = new StudentServiceImpl();
	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		// Add some sample data
		try {
			studentService.addStudent(new Student("STU001", "John Smith", 3.75, 1));
			studentService.addStudent(new Student("STU002", "Emma Johnson", 4.00, 2));
			studentService.addStudent(new Student("STU003", "Michael Brown", 3.25, 3));
			studentService.addStudent(new Student("STU004", "Sarah Davis", 3.90, 4));
			studentService.addStudent(new Student("STU005", "James Wilson", 3.50, 5));
		} catch (Exception e) {
			// This shouldn't happen with valid sample data
			System.out.println("Error initializing sample data: " + e.getMessage());
		}

		while (true) {
			printMenu();
			try {
				int choice = scanner.nextInt();
				scanner.nextLine(); // Consume the newline character

				switch (choice) {
					case 1:
						addStudentUI();
						break;
					case 2:
						deleteStudentUI();
						break;
					case 3:
						searchStudentUI();
						break;
					case 4:
						displayAllStudentsUI();
						break;
					case 5:
						System.out.println("Exiting application. Goodbye!");
						return; // Exit the main method, terminating the program
					default:
						System.out.println("Invalid choice. Please enter a number between 1 and 5.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a number.");
				scanner.nextLine(); // Clear the invalid input
			}
			System.out.println("\nPress Enter to continue...");
			scanner.nextLine();
		}
	}

	private static void printMenu() {
		System.out.println("\n--- Student Management System ---");
		System.out.println("1. Add a new student");
		System.out.println("2. Delete a student by ID");
		System.out.println("3. Search for a student by name");
		System.out.println("4. Display all students");
		System.out.println("5. Exit");
		System.out.print("Enter your choice: ");
	}

	private static void addStudentUI() {
		try {
			System.out.print("Enter list ID: ");
			int id = scanner.nextInt();
			scanner.nextLine(); // Consume newline

			System.out.print("Enter University ID: ");
			String studentId = scanner.nextLine();

			System.out.print("Enter Full Name (max 50 chars): ");
			String name = scanner.nextLine();
			if(name.length() > 50) {
				System.out.println("Error: Name exceeds 50 characters.");
				return;
			}

			System.out.print("Enter GPA (0.0 - 4.0): ");
			double gpa = scanner.nextDouble();
			scanner.nextLine(); // Consume newline

			Student newStudent = new Student(studentId, name, gpa, id);
			studentService.addStudent(newStudent);
			System.out.println("Student added successfully!");

		} catch (InputMismatchException e) {
			System.out.println("Error: Invalid input type. Please check your values.");
			scanner.nextLine(); // Clear buffer
		} catch (DateTimeParseException e) {
			System.out.println("Error: Invalid date format. Please use YYYY-MM-DD.");
		}
		catch (DuplicateStudentException | InvalidGPAException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private static void deleteStudentUI() {
		try {
			System.out.print("Enter Student ID to delete: ");
			int id = scanner.nextInt();
			scanner.nextLine();

			studentService.deleteStudent(id);
			System.out.println("Student with ID " + id + " deleted successfully!");
		} catch (InputMismatchException e) {
			System.out.println("Error: Invalid ID. Please enter a number.");
			scanner.nextLine(); // Clear the buffer
		} catch (StudentNotFoundException e) {
			System.out.println("Error: Student with ID " + e.getMessage() + " not found.");
		}
	}

	private static void searchStudentUI() {
		System.out.print("Enter the name (or partial name) to search for: ");
		String name = scanner.nextLine();
		List<Student> results = studentService.searchStudent(name);

		if (results.isEmpty()) {
			System.out.println("No students found with that name.");
		} else {
			System.out.println("--- Search Results ---");
			printStudentHeader();
			results.forEach(System.out::println);
			printStudentFooter();
		}
	}

	private static void displayAllStudentsUI() {
		List<Student> students = studentService.getAllStudents();
		System.out.println("--- All Students ---");
		if (students.isEmpty()) {
			System.out.println("No students in the system yet.");
		} else {
			printStudentHeader();
			students.forEach(System.out::println);
			printStudentFooter();
		}
	}

	private static void printStudentHeader() {
		System.out.println("+------------+-----------------+---------------------------+--------+");
		System.out.println("| List ID    | University ID   | Full Name                 | GPA    |");
		System.out.println("+------------+-----------------+---------------------------+--------+");
	}

	private static void printStudentFooter() {
		System.out.println("+------------+-----------------+---------------------------+--------+");
	}
}