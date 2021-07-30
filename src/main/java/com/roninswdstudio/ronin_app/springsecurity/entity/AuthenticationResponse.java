package com.roninswdstudio.ronin_app.springsecurity.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AuthenticationResponse {

    private final String jwt;
    private List roles = new ArrayList<String>();

    public AuthenticationResponse(String jwt, UserDetails userDetails) {
        this.jwt = jwt;
        Iterator<? extends GrantedAuthority> authIterator = userDetails.getAuthorities().iterator();
        while(authIterator.hasNext()) {
            roles.add(authIterator.next().toString());
        }
    }

    public String getJwt() {
        return jwt;
    }

    public List<String> getRoles() { return roles; }
}
