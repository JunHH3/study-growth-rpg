package com.studygrowthrpg.auth.repository;

import com.studygrowthrpg.auth.enetity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailVerificationTokenRepository extends JpaRepository<User, Long> {
}
