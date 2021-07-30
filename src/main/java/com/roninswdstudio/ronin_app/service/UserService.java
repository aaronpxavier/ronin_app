package com.roninswdstudio.ronin_app.service;

import com.roninswdstudio.ronin_app.springsecurity.dao.UserDao;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserDao userDao;

    UserService(UserDao userDao) {
        this.userDao = userDao;
    }

//    User addUser(User user) {
//        return this.userDao.save(user);
//    }
}
