package com.roninswdstudio.ronin_app.springsecurity.services;

import com.roninswdstudio.ronin_app.springsecurity.models.RoninUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
public class JwtUtil {

    private final String SECRET_KEY = System.getenv("JWT_SECRET");

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public UserDetails extractUserDetails(String token) {
        Claims claims = extractAllClaims(token);
        Map<String, Object> map = claims.get("userDetails", HashMap.class);
        RoninUserDetails userDetails =
                new RoninUserDetails(
                        (String) map.get("username"),
                        (String) map.get("password"),
                        (ArrayList<LinkedHashMap<String, String>>) map.get("authorities")
                );
        return userDetails;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userDetails", userDetails);
        return createToken(claims);
    }

    private String createToken(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public  Boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch(SignatureException e) {
            return false;
        }
    }
}
