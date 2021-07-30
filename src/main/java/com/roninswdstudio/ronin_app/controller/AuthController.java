package com.roninswdstudio.ronin_app.controller;

import com.roninswdstudio.ronin_app.springsecurity.entity.AuthenticationRequest;
import com.roninswdstudio.ronin_app.springsecurity.entity.AuthenticationResponse;
import com.roninswdstudio.ronin_app.springsecurity.services.RoninUserDetailsService;
import com.roninswdstudio.ronin_app.springsecurity.services.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@RestController()
public class AuthController {

    private AuthenticationManager authenticationManager;
    private RoninUserDetailsService userDetailsService;
    private JwtUtil jwtTokenUtil;

    public AuthController(AuthenticationManager authenticationManager, RoninUserDetailsService userDetailsService, JwtUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> getToken(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username or Password is not valid");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt, userDetails));
    }

    //login end point for cookie based authentication.
    @PostMapping("/auth/login/webapp")
    public ResponseEntity<?> getTokenWebapp(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) {
        String jwt;
        UserDetails userDetails;
        Cookie cookie;
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username or Password is not valid");
        }

        userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        jwt = jwtTokenUtil.generateToken(userDetails);
        cookie = new Cookie("jwtToken", jwt);
        cookie.setHttpOnly(true);
        //cookie.setSecure(true);
        cookie.setDomain("localhost");
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok(new AuthenticationResponse(jwt, userDetails));
    }


}
