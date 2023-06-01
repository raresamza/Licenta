package com.example.baeldunginheritance.service;

import com.example.baeldunginheritance.DTO.*;
import com.example.baeldunginheritance.collection.Comment;
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

    CommentDisplayData addCommentToCourse(Comment comment);

    List<CommentDisplayData> getCourseComments(String courseCode,String lectureHeader);

    String deleteAllCourses();
}
