package com.example.baeldunginheritance.service;

import com.example.baeldunginheritance.DTO.*;
import com.example.baeldunginheritance.collection.User;
import com.example.baeldunginheritance.collection.VerificationToken;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> getUsers();

    User saveStudent(UserCreationDTO userCreationDTO);

    User saveTeacher(UserCreationDTO userCreationDTO);

    void delete(String id);

    void saveVerificationTokenForUser(String token, User user);

    String validateVerificationToken(String token);

    void deleteAllUsers();

    User findUserByEmail(String email);

    void createPasswordResetTokenForUser(User user, String token);

    String validatePasswordResetToken(String token);

    Optional<User> getUserByPasswordResetToken(String token);

    void changePassword(User user, String newPassword);

    boolean checkIfValidOldPassword(User user, String oldPassword);

    List<User> getStudents();

    User saveTeacherPhoto(UserCreationDTO userCreationDTO);

    User saveStudentPhoto(UserCreationDTO userCreationDTO);

    User updateEmail(UpdateEmailDTO newEmail);

    User addCourseToUser(AddUserToCourseDTO addUserToCourseDTO);

    VerificationToken generateNewVerificationToken(String oldToken);

    String getPhotoURLByEmail(String email);

    boolean isUserActive(String email);

    String getQuizCodeFromUser(String email);
}
