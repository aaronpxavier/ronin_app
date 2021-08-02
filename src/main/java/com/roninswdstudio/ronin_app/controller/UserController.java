package com.roninswdstudio.ronin_app.controller;

import com.roninswdstudio.ronin_app.service.UserService;
import com.roninswdstudio.ronin_app.springsecurity.entity.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;

@RestController
public class UserController {
    UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, value = "/admin/user/add")
    public ResponseEntity<?> addArtist(@RequestBody User user)  throws ParseException {
        try {
            user.getRoles().parallelStream().forEach(role -> role.setUser(user));
            return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            System.err.println(e);
           return new ResponseEntity<>("data integrity violation", HttpStatus.BAD_REQUEST);
        }
    }

}
