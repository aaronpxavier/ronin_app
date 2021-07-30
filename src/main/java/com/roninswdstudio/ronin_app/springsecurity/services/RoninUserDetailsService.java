package com.roninswdstudio.ronin_app.springsecurity.services;

import com.roninswdstudio.ronin_app.springsecurity.entity.RoninUserDetails;
import com.roninswdstudio.ronin_app.springsecurity.entity.User;
import com.roninswdstudio.ronin_app.springsecurity.dao.UserDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class RoninUserDetailsService implements UserDetailsService {

    private final UserDao userRepository;

    RoninUserDetailsService (UserDao userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        return user.map(RoninUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found: " + username));
    }
}
