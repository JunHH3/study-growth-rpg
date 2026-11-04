package com.studygrowthrpg.auth.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class EmailVerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private String tokenHash;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    protected EmailVerificationToken(){}

    public EmailVerificationToken(User user, String tokenHash) {
        this.user = user;
        this.tokenHash = tokenHash;
        this.expiresAt = LocalDateTime.now().plusMinutes(30);
    }

    public void renew(String tokenHash) {
        this.tokenHash = tokenHash;
        this.expiresAt = LocalDateTime.now().plusMinutes(30);
    }
}