package com.studentapp.service;

import com.studentapp.exception.DuplicateStudentException;
import com.studentapp.exception.InvalidGPAException;
import com.studentapp.exception.StudentNotFoundException;
import com.studentapp.model.Student;
import java.util.List;

/**
 * REVERTED: Simplified the interface to core requirements.
 */
public interface StudentService {
    void addStudent(Student student) throws DuplicateStudentException, InvalidGPAException;
    void deleteStudent(int studentId) throws StudentNotFoundException;
    List<Student> searchStudent(String name);
    List<Student> getAllStudents();
}