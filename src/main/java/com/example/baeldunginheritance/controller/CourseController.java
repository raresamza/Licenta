package com.example.baeldunginheritance.controller;


import com.example.baeldunginheritance.DTO.*;
import com.example.baeldunginheritance.collection.Comment;
import com.example.baeldunginheritance.collection.Course;
import com.example.baeldunginheritance.collection.Mapper;
import com.example.baeldunginheritance.collection.Solution;
import com.example.baeldunginheritance.service.CourseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/course")
@CrossOrigin(origins = {"https://sssssss.herokuapp.com",
        "http://localhost:3000/",
        "http://localhost:3000/*",
        "https://frotnend.vercel.app/",
        "http://localhost:3000/courses-tab/add",
        "https://frotnend.vercel.app/courses-tab/add",
        "http://localhost:3000/courses-tab",
        "http://localhost:3000/courses-tab/*",
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


    @PutMapping("/add/comment")
    public CommentDisplayData addCommentToCourse(@RequestBody Comment comment) {
        return courseService.addCommentToCourse(comment);
    }

    @PutMapping("/add/solution")
    public SolutionDisplayData addSolutionToCourse(@RequestBody Solution solution) {
        return courseService.addSolutionToCourse(solution);
    }

    @GetMapping("/solutions/{coruseCode}/{lectureHeader}")
    public List<SolutionDisplayData> getCourseSolutions(@PathVariable String coruseCode, @PathVariable String lectureHeader) {
        return courseService.getCourseSolutions(coruseCode,lectureHeader);
    }
    @GetMapping("/comments/{coruseCode}/{lectureHeader}")
    public List<CommentDisplayData> getCourseComments(@PathVariable String coruseCode, @PathVariable String lectureHeader) {
        return courseService.getCourseComments(coruseCode,lectureHeader);
    }

    @PutMapping("/add/tests")
    public String addTestsToCourse(@RequestBody AddTestDTO addTestDTO) {
        return courseService.addTestsToCourse(addTestDTO);
    }

    @PutMapping("/upvote")
    public Integer upvote(@RequestBody VoteDTO voteDTO) {
        return courseService.upvote(voteDTO);
    }

    @PutMapping("/downvote")
    public Integer downvote(@RequestBody VoteDTO voteDTO) {
        System.out.println(voteDTO.toString());
        return courseService.downvote(voteDTO);
    }

    @GetMapping("/get/upvotes/{coruseCode}/{lectureHeader}")
    public Integer getUpvotes(@PathVariable String coruseCode,@PathVariable String lectureHeader) {
        return courseService.getUpvotes(coruseCode,lectureHeader);
    }
    @GetMapping("/get/downvotes/{coruseCode}/{lectureHeader}")
    public Integer getDownvotes(@PathVariable String coruseCode,@PathVariable String lectureHeader) {
        return courseService.getDownvotes(coruseCode,lectureHeader);
    }


    @DeleteMapping("/deleteall/")
    public String deleteAllCourses() {
        return courseService.deleteAllCourses();
    }
}
