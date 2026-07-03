package org.example.config;


import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.filter.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;


    @Bean
    public SecurityFilterChain securityFilterChain( HttpSecurity http ) throws Exception {

        http.csrf(csrf -> csrf.disable()).exceptionHandling(

                    exception ->

                            exception.authenticationEntryPoint(
                                    (request, response, authException) -> {
                                response.sendError( HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized" );
                            } )
                ).sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ).authorizeHttpRequests(auth -> auth.requestMatchers("/signin","/signup")
                .permitAll()
                .anyRequest()
                .authenticated()
        ).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
