package com.example.userservice.filters;

import com.example.userservice.service.MyUserDetailsService;
import com.example.userservice.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //get Header from request
        String token = request.getHeader("Authorization");
        //extract token part from header
        String jwtToken = extractJWTToken(token);
        if (jwtToken != null) {
            //extract userName from using JwtToken
            String userName = jwtUtil.extractUsername(jwtToken);
            //validate token by passing userDetails
            validateTokenUsingUserDetails(userName, jwtToken, request);
        }
        filterChain.doFilter(request, response);
    }

    private void validateTokenUsingUserDetails(String userName, String jwtToken, HttpServletRequest request) {
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = myUserDetailsService.loadUserByUsername(userName);
            if (jwtUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }
        }
    }

    private String extractJWTToken(String token) {
        String jwtToken = null;
        if (token != null && token.startsWith("Bearer ")) {
            jwtToken = token.substring(7);
        }
        return jwtToken;
    }


}

