package dev.juanito.studysync.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import dev.juanito.studysync.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;

@Getter
@Component
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);
    
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expirationMillis;

    private Key getSigningKey() {
        // Decode the Base64 secret key to get the actual bytes
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Creates a JWT token for the given user
     */
    public String createToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());

        Date now = new Date(System.currentTimeMillis());
        Date expiration = new Date(System.currentTimeMillis() + expirationMillis);

        return Jwts.builder()
                .claims(claims)
                .subject(user.getEmail())
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Validates if the token is valid and not expired
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
                .build()
                .parseSignedClaims(token)
                .getPayload();
            return true;
        } catch (JwtException e) {
            logger.error("Token validation failed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Extracts the subject (email) from the token
     */
    public String getSubject(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    /**
     * Extracts the user ID from the token with proper error handling
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
                .build()
                .parseSignedClaims(token)
                .getPayload();

        Object idObj = claims.get("id");
        if (idObj == null) {
            throw new IllegalStateException("Token does not contain an 'id' claim");
        }

        if (idObj instanceof Number) {
            return ((Number) idObj).longValue();
        } else if (idObj instanceof String) {
            try {
                return Long.parseLong((String) idObj);
            } catch (NumberFormatException e) {
                throw new IllegalStateException("Invalid 'id' claim value: " + idObj);
            }
        } else {
            throw new IllegalStateException("Unsupported 'id' claim type: " + idObj.getClass());
        }
    }
}