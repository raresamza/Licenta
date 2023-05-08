package com.example.baeldunginheritance.collection;

import com.example.baeldunginheritance.DTO.*;
import com.example.baeldunginheritance.Utils.Role;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Mapper {



    private final PasswordEncoder passwordEncoder;

    public Mapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public StudentDTO toDTO(Student student) {
        String firstName=student.getFirstName();
        String lastName=student.getLastName();
        String uniName=student.getUniName();
        Integer age=student.getAge();

        return new StudentDTO(firstName,lastName,uniName,age);
    }

    public TeacherDTO toDTO(Teacher teacher) {
        String firstName=teacher.getFirstName();
        String lastName=teacher.getLastName();
        String uniName=teacher.getUniName();
        Integer age=teacher.getAge();

        return new TeacherDTO(firstName,lastName,uniName,age);
    }

    public UserDTO toDTO(User user) {
        String firstName=user.getFirstName();
        String lastName=user.getLastName();
        String uniName=user.getUniName();
        Integer age=user.getAge();
        String photURL=user.getPhotoURL();

        return new UserDTO(firstName,lastName,uniName,age,photURL);
    }

    public User toUser(UserCreationDTO userCreationDTO) {
        return new User(userCreationDTO.getFirstName(),userCreationDTO.getLastName(),userCreationDTO.getUniName(),
                userCreationDTO.getAge(),userCreationDTO.getEmail(),passwordEncoder.encode(userCreationDTO.getPassword()));
    }
    public User toUserPhoto(UserCreationDTO userCreationDTO) {
        return new User(userCreationDTO.getFirstName(),userCreationDTO.getLastName(),userCreationDTO.getUniName(),
                userCreationDTO.getAge(),userCreationDTO.getEmail(),passwordEncoder.encode(userCreationDTO.getPassword()),userCreationDTO.getPhotoURL());
    }
    public User toStudentUser(UserCreationDTO userCreationDTO) {
        return new User(userCreationDTO.getFirstName(),userCreationDTO.getLastName(),userCreationDTO.getUniName(),
                userCreationDTO.getAge(),Role.STUDENT,userCreationDTO.getEmail(),passwordEncoder.encode(userCreationDTO.getPassword()));
    }
    public User toStudentUserPhoto(UserCreationDTO userCreationDTO) {
        return new User(userCreationDTO.getFirstName(),userCreationDTO.getLastName(),userCreationDTO.getUniName(),
                userCreationDTO.getAge(),Role.STUDENT,userCreationDTO.getEmail(),passwordEncoder.encode(userCreationDTO.getPassword()),userCreationDTO.getPhotoURL());
    }
    public User toTeacherUser(UserCreationDTO userCreationDTO) {
        return new User(userCreationDTO.getFirstName(),userCreationDTO.getLastName(),userCreationDTO.getUniName(),
                userCreationDTO.getAge(),Role.TEACHER,userCreationDTO.getEmail(),passwordEncoder.encode(userCreationDTO.getPassword()));
    }
    public User toTeacherUserPhoto(UserCreationDTO userCreationDTO) {
        return new User(userCreationDTO.getFirstName(),userCreationDTO.getLastName(),userCreationDTO.getUniName(),
                userCreationDTO.getAge(),Role.TEACHER,userCreationDTO.getEmail(),passwordEncoder.encode(userCreationDTO.getPassword()),userCreationDTO.getPhotoURL());
    }

    public Teacher toTeacher(TeacherCreationDTO teacherDTO) {
        return new Teacher(teacherDTO.getFirstName(),teacherDTO.getLastName(),teacherDTO.getUniName(),
                teacherDTO.getAge(), Role.TEACHER,teacherDTO.getEmail(),teacherDTO.getPassword());
    }

    public Student toStudent(StudentCreationDTO studentDTO) {
//        System.out.println(passwordEncoder.encode(studentDTO.getPassword()));
        return new Student(studentDTO.getFirstName(),studentDTO.getLastName(),studentDTO.getUniName(),
                studentDTO.getAge(), Role.STUDENT,studentDTO.getEmail(),studentDTO.getPassword() );
    }

    public Course toCourse(CourseDTO courseDTO) {
        return new Course(courseDTO.getCourseDescription(), courseDTO.getTitle());
    }

    public CourseDTO toCourseDTO(Course course) {
        return new CourseDTO(course.getCourseCode(), course.getCourseDescription(), course.getTitle());
    }

    public Course toCourse(CourseCreationDTO courseCreationDTO) {
        return new Course(courseCreationDTO.getCourseDescription(), courseCreationDTO.getTitle());
    }

}
