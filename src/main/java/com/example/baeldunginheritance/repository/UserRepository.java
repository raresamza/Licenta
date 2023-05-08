package com.example.baeldunginheritance.repository;

import com.example.baeldunginheritance.collection.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {

    User findUserByEmail(String email);
    void deleteUserByEmail(String email);

}
