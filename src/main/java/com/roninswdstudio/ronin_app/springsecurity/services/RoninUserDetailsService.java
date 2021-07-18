package com.roninswdstudio.ronin_app.springsecurity.services;

import com.roninswdstudio.ronin_app.springsecurity.models.RoninUserDetails;
import com.roninswdstudio.ronin_app.springsecurity.models.User;
import com.roninswdstudio.ronin_app.springsecurity.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class RoninUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    RoninUserDetailsService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        System.out.println(user.get().getUsername() + " username test");
        System.out.println(user.get().getRoles().get(0));
        return user.map(RoninUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found: " + username));
    }
}
