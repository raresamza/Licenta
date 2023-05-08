package com.example.baeldunginheritance.service;

import com.example.baeldunginheritance.DTO.AddLectureDTO;
import com.example.baeldunginheritance.DTO.CourseCreationDTO;
import com.example.baeldunginheritance.DTO.CourseDTO;
import com.example.baeldunginheritance.collection.Course;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface CourseService {


    List<CourseDTO> getAllCourses();

    Course addCourse(CourseCreationDTO courseCreationDTO);


    Course addLectureToCourse(AddLectureDTO addLectureDTO);

    List<Course> getCoursesByEmail(String email);

    Course getCoursesByCourseCode(HttpServletRequest httpServletRequest);

    Course getCoursesByCourseId(String id);
}
