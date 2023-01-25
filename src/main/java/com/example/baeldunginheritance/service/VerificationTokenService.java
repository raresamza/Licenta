package com.example.baeldunginheritance.service;

import com.example.baeldunginheritance.collection.VerificationToken;

public interface VerificationTokenService {
    void deleteAllTokens();

    VerificationToken findByToken(String oldToken);
}
