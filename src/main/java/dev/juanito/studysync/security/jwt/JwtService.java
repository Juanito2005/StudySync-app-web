package dev.juanito.studysync.security.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import dev.juanito.studysync.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;


@Getter
@Component
public class JwtService {
    
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Double expiration;

    /*
     * Se crea el token por medio de "claims" como el user_id, correo, tiempo de expiración, y la firma (secret key)
     */
    public String createToken(User user) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("id", user.getId());

    return Jwts.builder()
            .claims(claims)
            .subject(user.getEmail())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + expiration.longValue()))
            .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), Jwts.SIG.HS256)
            .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();

            return true;
        } catch (Exception e) {
            System.err.println("Token validation failed: " + e.getMessage());
            return false;
        }
    }

    // // Método para obtener el usuario del token (lo necesitaremos después)
    // public String getSubject(String token) {
    //     return Jwts.parserBuilder()
    //             .setSigningKey(secretKey.getBytes())
    //             .build()
    //             .parseClaimsJws(token)
    //             .getBody()
    //             .getSubject(); // Obtiene el "subject" (email en nuestro caso)
    // }

    // // Método para obtener el ID del usuario del token (también lo necesitaremos)
    // public Long getUserIdFromToken(String token) {
    //     Claims claims = Jwts.parserBuilder()
    //             .setSigningKey(secretKey.getBytes())
    //             .build()
    //             .parseClaimsJws(token)
    //             .getBody();
    //     // Necesitaremos convertir el Object a Long
    //     return ((Number) claims.get("id")).longValue();
    // }
}
