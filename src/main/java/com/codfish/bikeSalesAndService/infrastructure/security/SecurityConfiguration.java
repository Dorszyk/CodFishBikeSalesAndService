package com.codfish.bikeSalesAndService.infrastructure.security;

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
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration{
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            HttpSecurity http,
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService
    )
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    @Bean
    @ConditionalOnProperty(value = "spring.security.enabled", havingValue = "true", matchIfMissing = true)
    SecurityFilterChain securityEnabled(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/login", "/error", "/images/**").permitAll()
                .requestMatchers("/personRepairing/**","/add_update_parts/**","/add_part/**","/update_part/**","/delete_part/**",
                        "/add_update_services/**","/add_service/**","/update_service/**","/delete_service**",
                        "/add_update_person_repairing/**","/add_person_repairing/**","/update_person_repairing/**","/delete_person_repairing/**")
                .hasAnyAuthority("PERSON_REPAIRING")

                .requestMatchers("/salesman/**", "/purchase/**","/purchase-new-customer/**", "/add_bike/**","/update_bike/**",
                        "/deleteBike/{serial}/**","/add_update_salesman/**",
                        "/add_salesman/**","/update_salesman/**","/delete_salesman/**")
                .hasAnyAuthority("SALESMAN")

                .requestMatchers("/", "/bike/**", "/images/bike.png","/images/oh_no.png","/service/**","/customers-purchases/**","/invoices-purchases/**","/add_customer/**","/deleteCustomer/**").hasAnyAuthority("PERSON_REPAIRING", "SALESMAN")
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
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
    SecurityFilterChain securityDisabled(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeHttpRequests()
                .anyRequest()
                .permitAll();

        return http.build();
    }
}
