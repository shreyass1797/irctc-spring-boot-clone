package com.shreyass.irctc_clone.security;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor 
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Grab the "Authorization" header from the request
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // 2. If there is no header, or it doesn't start with "Bearer ", reject it (pass it down the chain to fail)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extract the token (Remove "Bearer " from the string)
        jwt = authHeader.substring(7);
        
        try {
            username = jwtUtil.extractUsername(jwt);
            
            // 4. If we found a username and they aren't already authenticated in this request cycle
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                
                // 5. Validate the math!
                if (jwtUtil.validateToken(jwt, username)) {
                    
                    // 6. The token is legit. Tell Spring Security that this user is authenticated by creating an Authentication object and setting it in the SecurityContext.
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            username, null, new ArrayList<>());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // If the token is expired or mathematically forged, it throws an exception. 
            // We catch it and do nothing, which leaves the user unauthenticated (403 Forbidden).
            System.out.println("Invalid or expired JWT token!");
        }

        // 7. Pass the request to the next step
        filterChain.doFilter(request, response);
    }
}