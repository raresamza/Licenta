package com.example.baeldunginheritance.controller;

import com.example.baeldunginheritance.DTO.TeacherCreationDTO;
import com.example.baeldunginheritance.DTO.TeacherDTO;
import com.example.baeldunginheritance.collection.Mapper;
import com.example.baeldunginheritance.collection.Teacher;
import com.example.baeldunginheritance.service.TeacherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    private final TeacherService teacherService;

    private final Mapper mapper;

    public TeacherController(TeacherService teacherService, Mapper mapper) {
        this.teacherService = teacherService;
        this.mapper = mapper;
    }

    @GetMapping
    @ResponseBody
    public List<TeacherDTO> getTeachers() {
        return teacherService.getTeachers();
    }

    @PostMapping
    @ResponseBody
    public Teacher save(@RequestBody TeacherCreationDTO teacherDTO) {
        return teacherService.save(teacherDTO);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        teacherService.delete(id);
    }
}

// ob lei