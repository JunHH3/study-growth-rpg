package com.studygrowthrpg.auth;

import com.studygrowthrpg.auth.dto.SignupRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void signUp(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateUserException("이미 가입된 이메일입니다.");
        }

        if (userRepository.existsByNickname(request.getNickname())) {
            throw new DuplicateUserException("이미 사용 중인 닉네임입니다.");
        }

        String passwordHash = passwordEncoder.encode(request.getPassword());

        User user = new User(request.getEmail()
                , request.getNickname(), passwordHash);
        userRepository.save(user);
    }
}