package com.example.baeldunginheritance.service;

import com.example.baeldunginheritance.collection.VerificationToken;
import com.example.baeldunginheritance.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {



    private final VerificationTokenRepository verificationTokenRepository;

    public VerificationTokenServiceImpl(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Override
    public void deleteAllTokens() {
        verificationTokenRepository.deleteAll();
    }

    @Override
    public VerificationToken findByToken(String oldToken) {
        return verificationTokenRepository.findByToken(oldToken);
    }
}
