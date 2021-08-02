package com.roninswdstudio.ronin_app.service;

import com.roninswdstudio.ronin_app.dao.ArtistDao;
import com.roninswdstudio.ronin_app.springsecurity.dao.UserDao;
import com.roninswdstudio.ronin_app.springsecurity.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserDao userDao;
    private ArtistDao artistDao;
    private PasswordEncoder encoder;

    public UserService(UserDao userDao, ArtistDao artistDao, PasswordEncoder encoder) {
        this.userDao = userDao;
        this.artistDao = artistDao;
        this.encoder = encoder;
    }

    public User saveUser (User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userDao.save(user);
    }




}
