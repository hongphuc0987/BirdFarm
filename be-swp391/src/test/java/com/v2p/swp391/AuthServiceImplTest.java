package com.v2p.swp391;

import com.v2p.swp391.application.repository.UserRepository;
import com.v2p.swp391.application.request.LoginRequest;
import com.v2p.swp391.application.request.SignUpRequest;
import com.v2p.swp391.application.response.AuthResponse;
import com.v2p.swp391.application.service.impl.AuthServiceImpl;
import com.v2p.swp391.exception.AppException;
import com.v2p.swp391.security.TokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthServiceImpl")
public class AuthServiceImplTest {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImplTest.class);
    @InjectMocks
    private AuthServiceImpl authService;
    @Mock
    UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void signUp_EmailNotExist() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("tester@fpt.edu.vn");
        signUpRequest.setPassword("123456");
        signUpRequest.setName("Demo");

        when(userRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(signUpRequest.getPassword())).thenReturn("password encoded");

        assertDoesNotThrow(() -> authService.signUp(signUpRequest));

        verify(userRepository).existsByEmail(any(String.class));
        verify(passwordEncoder).encode(any(String.class));

    }
    @Test
    public void signUp_EmailExist() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("tester@fpt.edu.vn");
        signUpRequest.setPassword("123456");
        signUpRequest.setName("Demo");

        when(userRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(true);

        assertThrows(AppException.class,() -> authService.signUp(signUpRequest));

        verify(userRepository).existsByEmail(any(String.class));
    }

    @Test
    public void signIn_AuthenticationSucess() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("tester@fpt.edu.vn");
        loginRequest.setPassword("123456");

        Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);
        when(tokenProvider.createToken(authentication)).thenReturn("fakeToken");

        AuthResponse authResponse = authService.signIn(loginRequest);

        assertNotNull(authResponse);
        assertEquals("Bearer", authResponse.getTokenType());
        assertEquals("fakeToken", authResponse.getAccessToken());
    }
    @Test
    public void signIn_AuthenticationFailure() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("tester@fpt.edu.vn");
        loginRequest.setPassword("123456");

        when(authenticationManager.authenticate(any())).thenThrow(new AuthenticationException("Authentication failed"){});

        assertThrows(AuthenticationException.class, () -> authService.signIn(loginRequest));

        verify(authenticationManager).authenticate(any(Authentication.class));

    }
}
