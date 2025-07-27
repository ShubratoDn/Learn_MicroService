package com.microservice.learn.auth.service.configs.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtTokenUtil {
	

//    @Value("${jwt.secret-key}")
    private String SECRET_KEY = "THIS_IS_A_SECRET_KEY";

//    @Value("${jwt.expiration-time}")
    private long EXPIRATION_TIME = 100 * 60 * 60 * 1000; // 100 hour Expiration time in milliseconds
	

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);        
    }
    
    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        
        // Add user information to claims if it's a CustomUserDetails
        if (userDetails instanceof com.microservice.learn.auth.service.configs.security.CustomUserDetails) {
            com.microservice.learn.auth.service.configs.security.CustomUserDetails customUserDetails = 
                (com.microservice.learn.auth.service.configs.security.CustomUserDetails) userDetails;
            com.microservice.learn.auth.service.entities.User user = customUserDetails.getLoggedInUser();
            
            claims.put("email", user.getEmail());
            claims.put("role", user.getRole());
            claims.put("designation", user.getDesignation());
            claims.put("name", user.getName());
        }
        
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() +(1000 * 60 * 60 * 24)))
        		  .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}