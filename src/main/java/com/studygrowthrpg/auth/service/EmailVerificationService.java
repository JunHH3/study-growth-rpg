package com.studygrowthrpg.auth.service;

import com.studygrowthrpg.auth.entity.EmailVerificationToken;
import com.studygrowthrpg.auth.entity.User;
import com.studygrowthrpg.auth.repository.EmailVerificationTokenRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;

import static java.util.Base64.getUrlEncoder;

@Service
public class EmailVerificationService {

    private final EmailVerificationTokenRepository emailVerificationTokenRepository;

    public EmailVerificationService(EmailVerificationTokenRepository emailVerificationTokenRepository) {
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
    }

    private final SecureRandom secureRandom = new SecureRandom();
    private String generateToken() {
       byte[] tokenBytes = new byte[32];
       secureRandom.nextBytes(tokenBytes);
       return getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }

    private String hashToken(String rawToken) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] tokenBytes = rawToken.getBytes(StandardCharsets.UTF_8);
            byte[] hashBytes = digest.digest(tokenBytes);
            return getUrlEncoder().withoutPadding().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException();
        }
    }

    public String issueToken(User user) {
        String rawToken = generateToken();
        String tokenHash = hashToken(rawToken);
        EmailVerificationToken verificationToken;

        Optional<EmailVerificationToken> existingToken = emailVerificationTokenRepository.findByUser(user);
        if (existingToken.isPresent()) {
            verificationToken = existingToken.get();
            verificationToken.renew(tokenHash);
        } else {
            verificationToken = new EmailVerificationToken(user, tokenHash);
        }

        emailVerificationTokenRepository.save(verificationToken);
        return rawToken;
    }
}