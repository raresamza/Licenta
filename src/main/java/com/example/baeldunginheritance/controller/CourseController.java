package com.example.baeldunginheritance.controller;


import com.example.baeldunginheritance.DTO.AddLectureDTO;
import com.example.baeldunginheritance.DTO.AddUserToCourseDTO;
import com.example.baeldunginheritance.DTO.CourseCreationDTO;
import com.example.baeldunginheritance.DTO.CourseDTO;
import com.example.baeldunginheritance.collection.Course;
import com.example.baeldunginheritance.collection.Mapper;
import com.example.baeldunginheritance.service.CourseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/course")
@CrossOrigin(origins = {"https://sssssss.herokuapp.com",
        "http://localhost:3000/",
        "https://frotnend.vercel.app/",
        "http://localhost:3000/courses-tab/add",
        "https://frotnend.vercel.app/courses-tab/add",
        "http://localhost:3000/courses-tab",
        "https://frotnend.vercel.app/courses-tab",
})
public class CourseController {


    private final CourseService courseService;

    private final Mapper mapper;

    public CourseController(CourseService courseService, Mapper mapper) {
        this.courseService = courseService;
        this.mapper = mapper;
    }

    @GetMapping
    @ResponseBody
    public List<CourseDTO> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{email}")
    @ResponseBody
    public List<Course> getCoursesByEmail(@PathVariable String email) {
        return courseService.getCoursesByEmail(email);
    }

    @GetMapping("/get-by-code/**")
    @ResponseBody
    public Course getCoursesByCourseCode(HttpServletRequest httpServletRequest) {
//        System.out.println(httpServletRequest.getRequestURI()
//                .split(httpServletRequest.getContextPath() + "/get-by-code/")[1]);
////        return null;
        System.out.println(Arrays.toString(httpServletRequest.getRequestURI().split(httpServletRequest.getContextPath())));
        return courseService.getCoursesByCourseCode(httpServletRequest);
    }

    @GetMapping("/get-by-id/{id}")
    @ResponseBody
    public Course getCourseById(@PathVariable String id) {
        return courseService.getCoursesByCourseId(id);
    }


    @PostMapping("/add")
    @ResponseBody
    public Course addCourse(@RequestBody CourseCreationDTO courseCreationDTO) {
        return courseService.addCourse(courseCreationDTO);
    }



    @PutMapping("/add/lecture")
    @ResponseBody
    public Course addLectureToCourse(@RequestBody AddLectureDTO addLectureDTO) {
        return courseService.addLectureToCourse(addLectureDTO);
    }
}
