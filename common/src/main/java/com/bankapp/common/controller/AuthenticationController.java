package com.bankapp.common.controller;

import com.bankapp.common.security.JwtRequest;
import com.bankapp.common.security.JwtResponse;
import com.bankapp.common.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping
    public JwtResponse createAuthenticationToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            final String jwt = jwtTokenProvider.generateToken(userDetails.getUsername());

            return new JwtResponse(jwt);
        } catch (Exception e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
