package com.roninswdstudio.ronin_app.springsecurity.services;

import com.roninswdstudio.ronin_app.springsecurity.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final byte[] SECRET_KEY = Base64.getDecoder().decode(System.getenv("JWT_SECRET"));

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public static String getJWTFromCookie(HttpServletRequest request) {
        Cookie cookies[] = request.getCookies();
        if(cookies != null && cookies.length > 0)
            return Arrays.stream(request.getCookies())
                    .filter(c -> c.getName().equals("jwtToken"))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
        else
            return null;
    }

    public User extractUser(String token) {
        Claims claims = extractAllClaims(token);
        User user  = new User(claims.get("user", LinkedHashMap.class));
        return user;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .claim("user", user)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY))
                .compact();
    }


    public  Boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch(SignatureException e) {
            return false;
        }
    }
}
