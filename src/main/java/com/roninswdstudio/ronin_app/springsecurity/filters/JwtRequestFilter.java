package com.roninswdstudio.ronin_app.springsecurity.filters;

import com.roninswdstudio.ronin_app.springsecurity.services.JwtUtil;
import com.roninswdstudio.ronin_app.springsecurity.services.RoninUserDetailsService;
import io.jsonwebtoken.Jwt;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private RoninUserDetailsService userDetailsService;
    private JwtUtil jwtUtil;

    public JwtRequestFilter(RoninUserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        UserDetails userDetails;
        Cookie [] cookies = request.getCookies();
        UsernamePasswordAuthenticationToken userPassAuthToken;
        String jwt = authorizationHeader != null && authorizationHeader.length() > 7 ? authorizationHeader.substring(7) : null;
        // code block executes if cookie based authentication is being used.
        if(jwt == null && cookies != null) {
            jwt = Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals("jwtToken"))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
        }
        if(jwt != null && jwtUtil.validateToken(jwt)) {
            userDetails = jwtUtil.extractUserDetails(jwt);
            userPassAuthToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            userPassAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(userPassAuthToken);
        }
        chain.doFilter(request, response);
    }
}
