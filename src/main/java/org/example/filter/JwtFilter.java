package org.example.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.example.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class JwtFilter implements Filter {

    private final JwtService jwtService;

    @Autowired
    public JwtFilter (JwtService jwtService){
        this.jwtService = jwtService;


    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authHeader = httpRequest.getHeader("Authorization");


        boolean isLoginReq = "/signin".equals(httpRequest.getRequestURI());
        if(!isLoginReq && (authHeader == null || !authHeader.startsWith("Bearer "))){


            return;
        }

        String token = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(token);

        System.out.println("This is userEmail::"+userEmail);


        filterChain.doFilter(request, response);

    }
}
