package com.example.baeldunginheritance.service;


import com.example.baeldunginheritance.DTO.TeacherDTO;
import com.example.baeldunginheritance.DTO.UserCreationDTO;
import com.example.baeldunginheritance.DTO.UserDTO;
import com.example.baeldunginheritance.Utils.Role;
import com.example.baeldunginheritance.collection.Mapper;
import com.example.baeldunginheritance.collection.Teacher;
import com.example.baeldunginheritance.collection.User;
import com.example.baeldunginheritance.collection.VerificationToken;
import com.example.baeldunginheritance.repository.UserRepository;
import com.example.baeldunginheritance.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final VerificationTokenRepository verificationTokenRepository;

    private final Mapper mapper;

    public UserServiceImpl(UserRepository userRepository, Mapper mapper, VerificationTokenRepository verificationTokenRepository) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.verificationTokenRepository = verificationTokenRepository;
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
    public User saveStudent(UserCreationDTO userCreationDTO) {
        User student=mapper.toUser(userCreationDTO);
        student.setRole(Role.STUDENT);
//        User test=userRepository.findUserByEmail(student.getEmail());
        return userRepository.insert(student);
    }

    @Override
    public User saveTeacher(UserCreationDTO userCreationDTO) {
        User teacher=mapper.toUser(userCreationDTO);
        teacher.setRole(Role.TEACHER);
        return userRepository.insert(teacher);
    }

    @Override
    public void delete(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public void saveVerificationTokenForUser(String token, User user) {
        VerificationToken verificationToken=new VerificationToken(token,user);
        verificationTokenRepository.insert(verificationToken);
    }

    @Override
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if(verificationToken==null) {
            return "Invalid token";
        }

        User user=verificationToken.getUser();
        Calendar calendar=Calendar.getInstance();
        if(verificationToken.getExpirationTime().getTime()-calendar.getTime().getTime()<=0) {
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

//    @Override
//    public VerificationToken generateNewVerificationToken(String oldToken) {
//        VerificationToken verificationToken=verificationTokenRepository.findByToken(oldToken);
//        verificationToken.setToken(UUID.randomUUID().toString());
//        verificationTokenRepository.insert(verificationToken);
//        return verificationToken;
//    }
}
