package com.example.baeldunginheritance.service;


import com.example.baeldunginheritance.DTO.*;
import com.example.baeldunginheritance.collection.*;
import com.example.baeldunginheritance.repository.CourseRepository;
import com.example.baeldunginheritance.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {


    private final CourseRepository courseRepository;

    private final UserRepository userRepository;

    private final Mapper mapper;

    public CourseServiceImpl(CourseRepository courseRepository, UserRepository userRepository, Mapper mapper) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        List<CourseDTO> courseDTOS = new ArrayList<>();
        for (Course c : courses) {
            CourseDTO courseDTO = mapper.toCourseDTO(c);
            courseDTOS.add(courseDTO);
        }

        return courseDTOS;
    }

    @Override
    public Course addCourse(CourseCreationDTO courseCreationDTO) {
        Course course = mapper.toCourse(courseCreationDTO);
        return courseRepository.insert(course);
    }


    @Override
    public Course addLectureToCourse(AddLectureDTO addLectureDTO) {
        Course course = courseRepository.findByCourseCode(addLectureDTO.getCourseCode());
        if (course == null) {
            throw new UsernameNotFoundException("Course not found please enter a valid code");
        }
        System.out.println(addLectureDTO);
        course.addLecture(addLectureDTO.getLecture());

        return courseRepository.save(course);
    }

    @Override
    public List<Course> getCoursesByEmail(String email) {

        List<Course> courses = courseRepository.findAll();

        List<Course> userCourses = new ArrayList<>();
        for (Course course : courses) {
            if (course.getUsersEmails().contains(email)) {
                userCourses.add(course);
            }
        }
        if (userCourses.isEmpty()) {
            throw new UsernameNotFoundException("no courses found for this user");
        }

        return userCourses;
    }

    @Override
    public Course getCoursesByCourseCode(HttpServletRequest httpServletRequest) {
        System.out.println(httpServletRequest.getRequestURI()
                .split(httpServletRequest.getContextPath() + "/get-by-code/")[1]);
        List<Course> coruse = courseRepository.findAll();
        for (Course c : coruse) {
            System.out.println(c.getCourseCode() + "gotten");
            if (c.getCourseCode().equals(httpServletRequest.getRequestURI()
                    .split(httpServletRequest.getContextPath() + "/get-by-code/")[1])) {
                System.out.println("sex");
            }
        }
        return courseRepository.findByCourseCode(httpServletRequest.getRequestURI()
                .split(httpServletRequest.getContextPath() + "/get-by-code/")[1]);
    }


    @Override
    public Course getCoursesByCourseId(String id) {
        return null;
    }

    @Override
    public CommentDisplayData addCommentToCourse(Comment comment) {
        CommentDisplayData commentDisplayData=new CommentDisplayData();
        Course course = courseRepository.findByCourseCode(comment.getCoruseCode());
        boolean lectureFlag = false;

        if (course != null) {
            if (!(course.getUsersEmails().contains(comment.getEmail()))) {
                throw new UsernameNotFoundException("user not enrolled into course");
            } else {
                for (Lecture l : course.getLectures()) {
                    if (l.getHeader().equals(comment.getLectureHeader())) {
                        User userByEmail = userRepository.findUserByEmail(comment.getEmail());
                        commentDisplayData.setComment(comment.getComment());
                        commentDisplayData.setPhotoUrl(userByEmail.getPhotoURL());
                        l.addComment(mapper.toCommentDTO(comment));
                        lectureFlag = true;
                    }
                }
            }
        } else {
            throw new IllegalStateException("Course not found");
        }
        if (!lectureFlag) {
            throw new IllegalStateException("Lecture not found");
        }
         courseRepository.save(course);
        return commentDisplayData;
    }

    @Override
    public List<CommentDisplayData> getCourseComments(String courseCode, String lectureHeader) {
        List<CommentDisplayData> commentList=new ArrayList<>();
        Course course = courseRepository.findByCourseCode(courseCode);
        boolean lectureFlag = false;

        if (course != null) {
            for (Lecture l : course.getLectures()) {
                if (l.getHeader().equals(lectureHeader)) {
                    lectureFlag=true;
                    System.out.println(l.getComments());
                    if(!(l.getComments().isEmpty())) {
                        for(CommentDTO c:l.getComments()) {
                            User userByEmail = userRepository.findUserByEmail(c.getEmail());
                            CommentDisplayData commentDisplayData=new CommentDisplayData(c.getComment(), userByEmail.getPhotoURL());
                            commentList.add(commentDisplayData);
                        }
                    }
                }
            }
        } else {
            throw new IllegalStateException("Course not found");
        }
        if (!lectureFlag) {
            throw new IllegalStateException("Lecture not found");
        }
        return commentList;
    }

    @Override
    public String deleteAllCourses() {
         courseRepository.deleteAll();
        return "Successful";
    }
}
