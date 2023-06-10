package com.example.baeldunginheritance.service;

import com.example.baeldunginheritance.DTO.*;
import com.example.baeldunginheritance.collection.Comment;
import com.example.baeldunginheritance.collection.Course;
import com.example.baeldunginheritance.collection.Solution;
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

    SolutionDisplayData addSolutionToCourse(Solution solution);

    List<SolutionDisplayData> getCourseSolutions(String coruseCode, String lectureHeader);

    String addTestsToCourse(AddTestDTO addTestDTO);

    Integer upvote(VoteDTO voteDTO);

    Integer downvote(VoteDTO voteDTO);

    Integer getUpvotes(String coruseCode, String lectureHeader);

    Integer getDownvotes(String coruseCode, String lectureHeader);

    List<String> addInputsToCourse(AddInputsDTO inputsDTO);

    List<String> getLectureInputs(String courseCode, String lectureHeader);

    String getLectureTests(String courseCode, String lectureHeader);
}
