package com.example.baeldunginheritance.service;

import com.example.baeldunginheritance.DTO.UserCreationDTO;
import com.example.baeldunginheritance.DTO.UserDTO;
import com.example.baeldunginheritance.collection.User;
import com.example.baeldunginheritance.collection.VerificationToken;

import java.util.List;

public interface UserService {
    List<UserDTO> getUsers();

    User saveStudent(UserCreationDTO userCreationDTO);

    User saveTeacher(UserCreationDTO userCreationDTO);

    void delete(String id);

    void saveVerificationTokenForUser(String token, User user);

    String validateVerificationToken(String token);

    void deleteAllUsers();

//    VerificationToken generateNewVerificationToken(String oldToken);
}
