package org.example.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class JwtFilter implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authHeader = httpRequest.getHeader("Authorization");

        boolean isLoginReq = "/signin".equals(httpRequest.getRequestURI());

        System.out.println("LoginReq is::"+isLoginReq);

        if(!isLoginReq && (authHeader == null || !authHeader.startsWith("Bearer "))){
            System.out.println("Not an authorized request");

            return;
        }


        filterChain.doFilter(request, response);

    }
}
