package com.wargame.service;

import com.wargame.domain.Race;
import com.wargame.dto.incoming.AuthenticationRequest;
import com.wargame.dto.incoming.AuthenticationResponse;
import com.wargame.config.JWTProcessor;
import com.wargame.dto.incoming.RegisterRequestDTO;
import com.wargame.domain.CustomUser;
import com.wargame.domain.UserRole;
import com.wargame.dto.outgoing.RaceListDTO;
import com.wargame.repository.CustomUserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserService {

    private CustomUserRepository customUserRepository;
    private PasswordEncoder passwordEncoder;
    private JWTProcessor processor;
    private AuthenticationManager authenticationManager;

    public CustomUserService(CustomUserRepository customUserRepository,
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
        cUser.setTurns(20);
        if (customUserRepository.findAllByEmail(cUser.getEmail()).orElse(null) == null){
            customUserRepository.save(cUser);
            String jwt = processor.generateToken(cUser);
            return AuthenticationResponse.builder().token(jwt).build();
        } else {
            return null;
        }
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

    public List<RaceListDTO> raceLister() {
        return Arrays.stream(Race.values())
                .map(race -> {
                    RaceListDTO dto = new RaceListDTO();
                    dto.setRaceName(race.getDisplayName());
                    return dto;})
                .collect(Collectors.toList());
    }
}
