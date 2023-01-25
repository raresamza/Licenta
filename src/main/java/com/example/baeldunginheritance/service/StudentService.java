package com.example.baeldunginheritance.service;

import com.example.baeldunginheritance.DTO.StudentCreationDTO;
import com.example.baeldunginheritance.DTO.StudentDTO;
import com.example.baeldunginheritance.collection.Student;

import java.util.List;

public interface StudentService {
    List<StudentDTO> getStudents();

    Student save(StudentCreationDTO studentDTO);

    void delete(String id);
}
