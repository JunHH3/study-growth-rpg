package com.studygrowthrpg.auth.repository;

import com.studygrowthrpg.auth.entity.EmailVerificationToken;
import com.studygrowthrpg.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, Long> {

    Optional<EmailVerificationToken> findByTokenHash(String tokenHash);
    Optional<EmailVerificationToken> findByUser(User user);
}
