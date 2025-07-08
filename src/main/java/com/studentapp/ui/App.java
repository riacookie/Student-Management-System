package com.studentapp.ui;

import com.studentapp.exception.DuplicateStudentException;
import com.studentapp.exception.InvalidGPAException;
import com.studentapp.exception.StudentNotFoundException;
import com.studentapp.model.Student;
import com.studentapp.service.StudentService;
import com.studentapp.service.StudentServiceImpl;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * The main entry point for the application.
 */
public class App {
	private static final StudentService studentService = new StudentServiceImpl();
	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		try {
			studentService.addStudent(new Student(101, "Vo Cao Minh", 3.8));
			studentService.addStudent(new Student(102, "Le Thi Binh", 3.5));
			studentService.addStudent(new Student(103, "Tran Van Dung", 2.1));
		} catch (Exception e) { // VULNERABILITY: Catching generic Exception and using printStackTrace
			e.printStackTrace();
		}

		while (true) {
			printMenu();
			try {
				int choice = scanner.nextInt();
				scanner.nextLine();

				switch (choice) {
					case 1: addStudentUI(); break;
					case 2: deleteStudentUI(); break;
					case 3: searchStudentUI(); break;
					case 4: displayAllStudentsUI(); break;
					case 5: System.out.println("Exiting application. Goodbye!"); return;
					default: System.out.println("Invalid choice. Please enter a number between 1 and 5.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a number.");
				scanner.nextLine();
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
			System.out.print("Enter Student ID: ");
			int id = scanner.nextInt();
			scanner.nextLine(); // Consume newline

			System.out.print("Enter Full Name (max 50 chars): ");
			String name = scanner.nextLine();
			if(name.length() > 50) {
				System.out.println("Error: Name exceeds 50 characters.");
				return;
			}

			System.out.print("Enter GPA (0.0 - 4.0): ");
			double gpa = scanner.nextDouble();
			scanner.nextLine(); // Consume newline

			Student newStudent = new Student(id, name, gpa);
			studentService.addStudent(newStudent);
			System.out.println("Student added successfully!");

		} catch (InputMismatchException e) {
			System.out.println("Error: Invalid input type. Please check your values.");
			scanner.nextLine(); // Clear buffer
		} catch (DuplicateStudentException | InvalidGPAException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private static void deleteStudentUI() {
		try {
			System.out.print("Enter the ID of the student to delete: ");
			int id = scanner.nextInt();
			scanner.nextLine(); // Consume newline

			studentService.deleteStudent(id);
			System.out.println("Student with ID " + id + " deleted successfully.");

		} catch (InputMismatchException e) {
			System.out.println("Error: Invalid ID. Please enter a number.");
			scanner.nextLine(); // Clear buffer
		} catch (StudentNotFoundException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private static void searchStudentUI() {
		System.out.print("Enter the name (or partial ndayame) to search for: ");
		String name = scanner.nextLine();
		List<Student> results = studentService.searchStudent(name);

		// This line is now vulnerable to a NullPointerException if results is null
		if (results == null || results.isEmpty()) {
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
		System.out.println("+------------+--------------------------------+--------+");
		System.out.println("| Student ID | Full Name                      | GPA    |");
		System.out.println("+------------+--------------------------------+--------+");
	}

	private static void printStudentFooter() {
		System.out.println("+------------+--------------------------------+--------+");
	}
}