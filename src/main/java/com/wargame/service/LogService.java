package com.wargame.service;

import com.wargame.domain.CustomUser;
import com.wargame.domain.Log;
import com.wargame.dto.outgoing.LogListDTO;
import com.wargame.repository.CustomUserRepository;
import com.wargame.repository.LogRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LogService {
    private LogRepository logRepository;
    private CustomUserRepository customUserRepository;

    public LogService(LogRepository logRepository, CustomUserRepository customUserRepository) {
        this.logRepository = logRepository;
        this.customUserRepository = customUserRepository;
    }

    public List<LogListDTO> logLister(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails loggedInUser = (UserDetails) authentication.getPrincipal();
        CustomUser owner = customUserRepository.findAllByEmail(loggedInUser.getUsername()).orElse(null);

        List<LogListDTO> logs = logRepository.findAllById(owner.getId()).stream().map(LogListDTO::new).collect(Collectors.toList());
        System.out.println(logs);

        return logs;
    }
}
