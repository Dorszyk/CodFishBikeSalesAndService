package com.codfish.bikeSalesAndService.infrastructure.security;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity httpSecurity,
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService
    )
        throws Exception{
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    @Bean
    @ConditionalOnProperty(value = "spring.security.enabled",havingValue = "true",matchIfMissing = true)
    SecurityFilterChain securityFilterChainEnabled(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests()
                .requestMatchers("/login","/error").permitAll()
                .requestMatchers("/personRepairing/**").hasAnyAuthority("PERSON_REPAIRING")
                .requestMatchers("/salesman/**", "/purchase/**","/service/**").hasAnyAuthority("SALESMAN")
                .requestMatchers("/","/bike/**","/images/**").hasAnyAuthority("PERSON_REPAIRING","SALESMAN")
                .and()
                .formLogin()
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll();

        return http.build();
    }

    @Bean
    @ConditionalOnProperty(value = "spring.security.enabled", havingValue = "false")
    SecurityFilterChain securityFilterChainDisabled(HttpSecurity http)throws Exception{
        http.authorizeHttpRequests()
                .anyRequest()
                .permitAll();
        return http.build();
    }
}
