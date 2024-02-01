package com.wargame.service;

import com.wargame.dto.incoming.AuthenticationRequest;
import com.wargame.dto.incoming.AuthenticationResponse;
import com.wargame.config.JWTProcessor;
import com.wargame.dto.incoming.RegisterRequestDTO;
import com.wargame.domain.CustomUser;
import com.wargame.domain.UserRole;
import com.wargame.repository.CustomUserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthenticationService {

    private CustomUserRepository customUserRepository;
    private PasswordEncoder passwordEncoder;
    private JWTProcessor processor;
    private AuthenticationManager authenticationManager;

    public AuthenticationService(CustomUserRepository customUserRepository,
                                 PasswordEncoder passwordEncoder,
                                 JWTProcessor processor,
                                 AuthenticationManager authenticationManager) {
        this.customUserRepository = customUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.processor = processor;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegisterRequestDTO request) {
        CustomUser cUser = new CustomUser();
        cUser.setName(request.getName());
        cUser.setEmail(request.getEmail());
        cUser.setPassword(passwordEncoder.encode(request.getPassword()));
        cUser.setRole(UserRole.USER);
        cUser.setCreatedAt(LocalDateTime.now());
        customUserRepository.save(cUser);
        String jwt = processor.generateToken(cUser);
        return AuthenticationResponse.builder().token(jwt).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        CustomUser cUser = customUserRepository.findAllByEmail(request.getEmail()).orElse(null);
        String jwt = processor.generateToken(cUser);
        cUser.setToken(jwt);
        return AuthenticationResponse.builder().token(jwt).build();
    }
}
