package org.example.filter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.example.model.User;
import org.example.service.JwtService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


import java.io.IOException;


@Component
public class JwtFilter implements Filter {

    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    public JwtFilter (JwtService jwtService, UserService userService){
        this.jwtService = jwtService;
        this.userService = userService;

    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        try{

            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String authHeader = httpRequest.getHeader("Authorization");


            if(authHeader == null || !authHeader.startsWith("Bearer ")){

                filterChain.doFilter(request, response);
                return;
            }

            String accessToken = authHeader.substring(7);
            String userEmail = jwtService.extractUsername(accessToken);

            User userDetails = userService.getUser(userEmail);

           boolean isTokenValid = jwtService.isTokenValid(accessToken, userDetails);

           if (isTokenValid){

               UsernamePasswordAuthenticationToken authToken =
                       new UsernamePasswordAuthenticationToken(
                               userDetails,
                               null,
                               userDetails.getAuthorities()
                       );

               SecurityContextHolder.getContext().setAuthentication(authToken);

               filterChain.doFilter(request, response);
           }

        } catch (ExpiredJwtException e) {

            System.out.println("JWT Token Expired");
        }
    }
}
