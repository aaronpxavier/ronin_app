package com.roninswdstudio.ronin_app.controller;

import com.roninswdstudio.ronin_app.service.UserService;
import com.roninswdstudio.ronin_app.springsecurity.entity.AuthenticationRequest;
import com.roninswdstudio.ronin_app.springsecurity.entity.AuthenticationResponse;
import com.roninswdstudio.ronin_app.springsecurity.entity.User;
import com.roninswdstudio.ronin_app.springsecurity.services.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@RestController()
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtTokenUtil;
    private UserService userService;

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtUtil jwtTokenUtil,
            UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> getToken(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username or Password is not valid");
        }
        final User user = userService.findUserByEmail(authenticationRequest.getUsername()).orElse(null);
        return ResponseEntity.ok(new AuthenticationResponse(jwtTokenUtil.generateToken(user), user));
    }

    //login end point for cookie based authentication.
    @PostMapping("/auth/login/webapp")
    public ResponseEntity<?> getTokenWebapp(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) {
        String jwt;
        Cookie cookie;
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username or Password is not valid");
        }

        final User user = userService.findUserByEmail(authenticationRequest.getUsername()).orElse(null);
        jwt = jwtTokenUtil.generateToken(user);
        cookie = new Cookie("jwtToken", jwt);
        cookie.setHttpOnly(true);
        //cookie.setSecure(true);
        cookie.setDomain("localhost");
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok(new AuthenticationResponse(jwt, user));
    }


}
