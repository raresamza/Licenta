package com.example.baeldunginheritance.service;


import com.example.baeldunginheritance.DTO.*;
import com.example.baeldunginheritance.Utils.Role;
import com.example.baeldunginheritance.collection.*;
import com.example.baeldunginheritance.repository.CourseRepository;
import com.example.baeldunginheritance.repository.PasswordResetTokenRepository;
import com.example.baeldunginheritance.repository.UserRepository;
import com.example.baeldunginheritance.repository.VerificationTokenRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final VerificationTokenRepository verificationTokenRepository;

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    private final CourseRepository courseRepository;


    private final Mapper mapper;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, Mapper mapper, VerificationTokenRepository verificationTokenRepository, PasswordResetTokenRepository passwordResetTokenRepository, MongoTemplate mongoTemplate, CourseRepository courseRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.verificationTokenRepository = verificationTokenRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;

        this.courseRepository = courseRepository;
    }

    @Override
    public List<UserDTO> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = new ArrayList<>();

        for (User u : users) {
            UserDTO userDTO = mapper.toDTO(u);
            userDTOs.add(userDTO);
        }

        return userDTOs;
    }

    @Override
    public List<User> getStudents() {
//        List<User> users = userRepository.findAll();
//        List<UserDTO> userDTOs = new ArrayList<>();
//
//
//
//        for (User u : users) {
//            System.out.println(u.getRole()+ " vs " + Role.STUDENT.toString());
//            System.out.println(u.getRole().getClass()+ " vs " + Role.STUDENT.toString().getClass());
//            if (u.getRole().toString().equals(Role.STUDENT.toString())) {
//                UserDTO userDTO = mapper.toDTO(u);
//                userDTOs.add(userDTO);
//            }
//        }
//
//        return userDTOs;
        return userRepository.findAll().stream().filter(user -> user.getRole().toString().equals(Role.STUDENT.toString())).toList();
    }



    @Override
    public User saveStudent(UserCreationDTO userCreationDTO) {
        User student = mapper.toUser(userCreationDTO);
        student.setRole(Role.STUDENT);
//        User test=userRepository.findUserByEmail(student.getEmail());
        return userRepository.insert(student);
    }

    @Override
    public User saveStudentPhoto(UserCreationDTO userCreationDTO) {
        User student = mapper.toUserPhoto(userCreationDTO);
        student.setRole(Role.STUDENT);
//        User test=userRepository.findUserByEmail(student.getEmail());
        return userRepository.insert(student);
    }

    @Override
    public User updateEmail( UpdateEmailDTO newEmail) {
        User user=userRepository.findUserByEmail(newEmail.getOldEmail());

        if(user==null) {
            throw new UsernameNotFoundException("User not found");
        } else {
            user.setEmail(newEmail.getNewEmail());
            System.out.println("new email is: "+newEmail);
            System.out.println("new Person: "+user.toString());
            return userRepository.save(user);
        }
//        return null;
    }

    @Override
    public User addCourseToUser(AddUserToCourseDTO addUserToCourseDTO) {
        Course course=courseRepository.findByCourseCode(addUserToCourseDTO.getCourseCode());
        if(course==null) {
            throw new UsernameNotFoundException("Course not found please enter a valid code");
        }
        User user= userRepository.findUserByEmail(addUserToCourseDTO.getEmail());
        if(user==null) {
            throw new UsernameNotFoundException("user not found please enter a valid email");
        }
        user.addCourse(course);
        if(!course.getUsersEmails().contains(addUserToCourseDTO.getEmail())) {
            course.addUser(addUserToCourseDTO.getEmail());
        }
        courseRepository.save(course);
        return userRepository.save(user);
    }

    @Override
    public User saveTeacherPhoto(UserCreationDTO userCreationDTO) {
        User teacher = mapper.toUserPhoto(userCreationDTO);
        teacher.setRole(Role.TEACHER);
        return userRepository.insert(teacher);
    }



    @Override
    public User saveTeacher(UserCreationDTO userCreationDTO) {
        User teacher = mapper.toUser(userCreationDTO);
        teacher.setRole(Role.TEACHER);
        return userRepository.insert(teacher);
    }


    @Override
    public void delete(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public void saveVerificationTokenForUser(String token, User user) {
        VerificationToken verificationToken = new VerificationToken(token, user);
        verificationTokenRepository.insert(verificationToken);
    }

    @Override
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null) {
            return "Invalid token";
        }

        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if (verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime() <= 0) {
            verificationTokenRepository.delete(verificationToken);
            return "Token expired";
        }
        user.setEnabled(true);
        userRepository.deleteUserByEmail(user.getEmail());
        userRepository.insert(user);
        return "Valid token";

    }

    @Override
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.insert(passwordResetToken);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);

        if (passwordResetToken == null) {
            return "Invalid token";
        }

        User user = passwordResetToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if (passwordResetToken.getExpirationTime().getTime() - calendar.getTime().getTime() <= 0) {
            passwordResetTokenRepository.delete(passwordResetToken);
            return "Token expired";
        }

        return "Valid token";
    }

    @Override
    public Optional<User> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
    }

    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }


    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken=verificationTokenRepository.findByToken(oldToken);
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationTokenRepository.insert(verificationToken);
        return verificationToken;
    }

    @Override
    public String getPhotoURLByEmail(String email) {
        User user=findUserByEmail(email);

        return user.getPhotoURL();
    }
}
