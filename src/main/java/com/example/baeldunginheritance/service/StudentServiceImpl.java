package com.example.baeldunginheritance.service;


import com.example.baeldunginheritance.DTO.StudentCreationDTO;
import com.example.baeldunginheritance.DTO.StudentDTO;
import com.example.baeldunginheritance.collection.Mapper;
import com.example.baeldunginheritance.collection.Student;
import com.example.baeldunginheritance.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepository;



    private final Mapper mapper;

    public StudentServiceImpl(StudentRepository studentRepository, Mapper mapper) {
        this.studentRepository = studentRepository;
        this.mapper = mapper;
    }

    @Override
    public List<StudentDTO> getStudents() {
        List<Student> students=studentRepository.findAll();
        List<StudentDTO> studentDTOs=new ArrayList<>();

        for(Student s:students) {
            StudentDTO studentDTO=mapper.toDTO(s);
            studentDTOs.add(studentDTO);
        }

        return studentDTOs;
    }

    @Override
    public Student save(StudentCreationDTO studentDTO) {
        Student student=mapper.toStudent(studentDTO);
        return studentRepository.insert(student);
    }

    @Override
    public void delete(String id) {
        studentRepository.deleteById(id);
    }

}
