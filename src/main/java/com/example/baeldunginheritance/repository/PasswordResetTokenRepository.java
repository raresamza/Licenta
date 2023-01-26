package com.example.baeldunginheritance.repository;

import com.example.baeldunginheritance.collection.PasswordResetToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PasswordResetTokenRepository extends MongoRepository<PasswordResetToken,String> {
    PasswordResetToken findByToken(String token);
}
