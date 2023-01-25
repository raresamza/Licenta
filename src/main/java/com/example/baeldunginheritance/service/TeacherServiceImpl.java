package com.example.baeldunginheritance.service;


import com.example.baeldunginheritance.DTO.TeacherCreationDTO;
import com.example.baeldunginheritance.DTO.TeacherDTO;
import com.example.baeldunginheritance.collection.Mapper;
import com.example.baeldunginheritance.collection.Teacher;
import com.example.baeldunginheritance.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    private final Mapper mapper;

    public TeacherServiceImpl(TeacherRepository teacherRepository, Mapper mapper) {
        this.teacherRepository = teacherRepository;
        this.mapper = mapper;
    }

    @Override
    public List<TeacherDTO> getTeachers() {

        List<Teacher> teachers = teacherRepository.findAll();
        List<TeacherDTO> teacherDTOs = new ArrayList<>();

        for (Teacher t : teachers) {
            TeacherDTO teacherDTO = mapper.toDTO(t);
            teacherDTOs.add(teacherDTO);
        }

        return teacherDTOs;
    }

    @Override
    public Teacher save(TeacherCreationDTO teacherDTO) {
        Teacher teacher=mapper.toTeacher(teacherDTO);
        return teacherRepository.insert(teacher);
    }

    @Override
    public void delete(String id) {
        teacherRepository.deleteById(id);
    }
}

