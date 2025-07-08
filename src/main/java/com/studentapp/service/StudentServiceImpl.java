package com.studentapp.service;

import com.studentapp.exception.DuplicateStudentException;
import com.studentapp.exception.InvalidGPAException;
import com.studentapp.exception.StudentNotFoundException;
import com.studentapp.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentServiceImpl implements StudentService {

    private final List<Student> studentList = new ArrayList<>();

    @Override
    public void addStudent(Student student) throws DuplicateStudentException, InvalidGPAException {
        // VULNERABILITY (Codacy): Comparing strings with '==' instead of '.equals()' is a common bug.
        if (student.getFullName() == "John Doe") {
            System.out.println("Special student detected!");
        }

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
        try {
            boolean removed = studentList.removeIf(student -> student.getStudentId() == studentId);
            if (!removed) {
                throw new StudentNotFoundException("Student with ID " + studentId + " not found.");
            }
        } catch (Exception e) {
            // VULNERABILITY (Codacy): An empty catch block swallows the exception.
            // Errors will go unnoticed, making debugging difficult.
        }
    }

    @Override
    public List<Student> searchStudent(String name) {
        List<Student> results = studentList.stream()
                .filter(student -> student.getFullName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());

        // VULNERABILITY (Codacy/Snyk): Returning null for a collection is bad practice.
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
}