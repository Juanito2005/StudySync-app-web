package dev.juanito.studysync.security.jwt;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import dev.juanito.studysync.security.UserPrincipal;
import io.jsonwebtoken.lang.Collections;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        
        // Step 1: Get the Authorization header
        String authHeader = request.getHeader("Authorization");

        /*
        * Step 2: Check if the header exist and starts with: "Bearer"
        * In the case the token isn't valid, the user continues to the Controller
        * Through the .doFilter() method
        */
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Step 3: If everything is ok extract the token (remove "Bearer " prefix)
        String jwt = authHeader.substring(7);

        // Step 4: VALIDATE TOKEN (with the method I created before in JwtService) and set authentication context
        if (jwtService.validateToken(jwt)) {
            // Extract user information from token
            String userEmail = jwtService.getSubject(jwt);
            Long userId = jwtService.getUserIdFromToken(jwt);
            
            // Create custom principal with both email and userId
            UserPrincipal userPrincipal = new UserPrincipal(userEmail, userId);
            
            // Create authentication object
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userPrincipal, null, Collections.emptyList());
            
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                // Tell Spring Security who is authenticated
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        
        // Continue to the next filter/controller
        filterChain.doFilter(request, response);
    }
}
