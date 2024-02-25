package com.jwt.demo.Springjwtauthentication.config;

import com.jwt.demo.Springjwtauthentication.jwt.JwtTokenFilter;
import com.jwt.demo.Springjwtauthentication.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        jsr250Enabled = true
)
public class SecurityConfig {

    @Autowired
    private JwtTokenFilter filter;

    @Autowired
    private UserRepository repository;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf()
            .disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling()
            .authenticationEntryPoint((request, response, authException) -> {response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());})
            .and()
            .authorizeRequests()
            .antMatchers("/auth/login")
                .permitAll()
                .antMatchers("/products/add").access("hasRole('ROLE_CUSTOMER')")
            .anyRequest()
                .authenticated();

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class).authenticationProvider(daoAuthenticationProvider());


        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(username -> repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User ".concat(username).concat(" not found"))));

        return provider;
    }
}
