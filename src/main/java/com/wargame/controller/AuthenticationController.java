package com.wargame.controller;

import com.wargame.dto.incoming.AuthenticationResponse;
import com.wargame.dto.incoming.AuthenticationRequest;
import com.wargame.dto.incoming.RegisterRequestDTO;
import com.wargame.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/reg")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequestDTO request){
        return new ResponseEntity<>(authenticationService.register(request), HttpStatus.CREATED);
    }
    @PostMapping("/auth")
    public ResponseEntity<AuthenticationResponse> auth(@RequestBody AuthenticationRequest request){
        return new ResponseEntity<>(authenticationService.authenticate(request), HttpStatus.OK);
    }
    @PostMapping("/logout")
    public ResponseEntity<AuthenticationResponse> lout(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
