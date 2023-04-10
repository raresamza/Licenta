package com.example.baeldunginheritance.controller;


import com.example.baeldunginheritance.DTO.StudentCreationDTO;
import com.example.baeldunginheritance.DTO.StudentDTO;
import com.example.baeldunginheritance.collection.Mapper;
import com.example.baeldunginheritance.collection.Student;
import com.example.baeldunginheritance.service.StudentService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    private final ApplicationEventPublisher publisher;

    private final Mapper mapper;

    public StudentController(StudentService studentService, Mapper mapper, ApplicationEventPublisher publisher) {
        this.studentService = studentService;
        this.mapper = mapper;
        this.publisher = publisher;
    }


    @GetMapping
    @ResponseBody
    public List<StudentDTO> getStudents() {
        return studentService.getStudents();
    }

    @PostMapping()
    @ResponseBody
    public Student save(@RequestBody StudentCreationDTO studentDTO) {
        return studentService.save(studentDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        studentService.delete(id);
    }
}
