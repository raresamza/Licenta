package com.example.baeldunginheritance.repository;

import com.example.baeldunginheritance.collection.User;
import com.example.baeldunginheritance.collection.VerificationToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends MongoRepository<VerificationToken,String> {
    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
}
