package com.roninswdstudio.ronin_app.service;

import com.roninswdstudio.ronin_app.springsecurity.dao.UserDao;
import com.roninswdstudio.ronin_app.springsecurity.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserDao userDao;
    private PasswordEncoder encoder;

    public UserService(UserDao userDao, PasswordEncoder encoder) {
        this.userDao = userDao;
        this.encoder = encoder;
    }

    public User saveUser (User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userDao.save(user);
    }

    public Optional<User> findUserByEmail(String email) {
        return userDao.findByEmail(email);
    }




}
