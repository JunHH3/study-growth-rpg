package com.studygrowthrpg.auth.service;

import com.studygrowthrpg.auth.entity.EmailVerificationToken;
import com.studygrowthrpg.auth.entity.User;
import com.studygrowthrpg.auth.repository.EmailVerificationTokenRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class EmailVerificationServiceTest {

    @Test
    void unknownTokenThrowsException() {

        // 1. 가짜 Repository(mock) 생성
        EmailVerificationTokenRepository repository =
                mock(EmailVerificationTokenRepository.class);

        // 2. when-thenReturn으로 가짜 응답 설정
        when(repository.findByTokenHash(anyString()))
                .thenReturn(Optional.empty());

        // 3. 가짜 Repository를 전달해 Service 생성
        EmailVerificationService service =
                new EmailVerificationService(repository);

        // 4. assertThrows로 예상한 예외 검증
        assertThrows(IllegalArgumentException.class,
                () -> service.verifyToken("wrong-token"));
    }

    @Test
    void expiredTokenThrowsException() {

        // 1. 가짜 Repository(mock) 생성
        EmailVerificationTokenRepository repository =
                mock(EmailVerificationTokenRepository.class);

        // 2. 만료된 가짜 토큰 생성
        EmailVerificationToken token =
                mock(EmailVerificationToken.class);

        when(token.isExpired()).thenReturn(true);

        // 3. Repository가 토큰을 반환하도록 설정
        when(repository.findByTokenHash(anyString()))
                .thenReturn(Optional.of(token));

        // 4. 실제 Service 생성
        EmailVerificationService service =
                new EmailVerificationService(repository);

        // 5. 만료된 토큰에서 예외가 발생하는지 확인
        assertThrows(IllegalArgumentException.class,
                () -> service.verifyToken("expired-token"));
    }

    @Test
    void validTokenVerifiesEmailAndDeletesToken(){

        // 1. 가짜 Repository 생성
        EmailVerificationTokenRepository repository =
                mock(EmailVerificationTokenRepository.class);

        // 2. 가짜 인증 토큰과 회원생성
        EmailVerificationToken token =
                mock(EmailVerificationToken.class);

        User user = mock(User.class);

        // 3. 유효한 토큰 상태 설정
        when(repository.findByTokenHash(anyString()))
                .thenReturn(Optional.of(token));

        when(token.isExpired()).thenReturn(false);
        when(token.getUser()).thenReturn(user);

        // 4. 실제 Service 생성
        EmailVerificationService service =
                new EmailVerificationService(repository);

        // 5. 인증 검증 메서드 실행
        service.verifyToken("valid-token");

        // 6. 인증 완료와 토큰 삭제 호출 여부 검사
        verify(user).verifyEmail();
        verify(repository).delete(token);
    }

    @Test
    void reusedTokenThrowsException(){

        // 1. 가짜 Repository 생성
        EmailVerificationTokenRepository repository =
                mock(EmailVerificationTokenRepository.class);

        // 2. 가짜 인증 토큰과 회원 생성
        EmailVerificationToken token =
                mock(EmailVerificationToken.class);

        User user = mock(User.class);

        // 3. Repository 조회 결과 및 토큰 상태 결정
        when(repository.findByTokenHash(anyString()))
                .thenReturn(Optional.of(token), Optional.empty());

        when(token.isExpired()).thenReturn(false);
        when(token.getUser()).thenReturn(user);

        // 4. 실제 Service 생성
        EmailVerificationService service =
                new EmailVerificationService(repository);

        // 5. 첫 번째 인증 요청
        service.verifyToken("valid-token");

        // 6. 같은 토큰으로 두 번째 인증 요청
        assertThrows(IllegalArgumentException.class,
                () -> service.verifyToken("valid-token"));

        // 7. 인증 완료 및 토큰 삭제가 각각 한 번 호출됐는지 검사
        verify(user).verifyEmail();
        verify(repository).delete(token);
    }
}