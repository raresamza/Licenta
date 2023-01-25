package com.example.baeldunginheritance.service;

import com.example.baeldunginheritance.DTO.TeacherCreationDTO;
import com.example.baeldunginheritance.DTO.TeacherDTO;
import com.example.baeldunginheritance.collection.Teacher;

import java.util.List;

public interface TeacherService {
    List<TeacherDTO> getTeachers();

    Teacher save(TeacherCreationDTO teacherDTO);

    void delete(String id);
}
