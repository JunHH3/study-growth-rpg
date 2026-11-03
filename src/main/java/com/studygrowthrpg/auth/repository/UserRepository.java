package com.studygrowthrpg.auth.repository;

import com.studygrowthrpg.auth.enetity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
}
