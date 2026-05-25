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

        System.out.println("🔥 Incoming request:");
        System.out.println("Method: " + httpRequest.getMethod());
        System.out.println("URL: " + httpRequest.getRequestURI());

        filterChain.doFilter(request, response);


        System.out.println("✅ Response sent back");
    }
}
