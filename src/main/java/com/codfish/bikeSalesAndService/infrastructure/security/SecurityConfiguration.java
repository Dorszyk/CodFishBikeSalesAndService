package com.codfish.bikeSalesAndService.infrastructure.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/images/**", "/css/**", "/js/**", "/favicon.ico");
    }

    @Bean
    @ConditionalOnProperty(value = "spring.security.enabled", havingValue = "true", matchIfMissing = true)
    SecurityFilterChain securityEnabled(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(openAccessUrls()).permitAll()
                        .requestMatchers(personRepairingUrls()).hasAnyAuthority("PERSON_REPAIRING")
                        .requestMatchers(salesmanUrls()).hasAnyAuthority("SALESMAN")
                        .requestMatchers(generalUrls()).hasAnyAuthority("PERSON_REPAIRING", "SALESMAN")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(accessDeniedHandler())
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(customAuthenticationSuccessHandler)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }

    private String[] openAccessUrls() {
        return new String[]{"/login", "/error", "/images/**", "/css/**", "/js/**", "/favicon.ico"};
    }

    private String[] personRepairingUrls() {
        return new String[]{"/personRepairing/**", "/add_update_parts/**", "/add_part/**", "/update_part/**", "/delete_part/**",
                "/add_update_services/**", "/add_service/**", "/update_service/**", "/delete_service**",
                "/add_update_person_repairing/**", "/add_person_repairing/**", "/update_person_repairing/**", "/delete_person_repairing/**"};
    }

    private String[] salesmanUrls() {
        return new String[]{"/salesman/**", "/purchase/**", "/purchase_new_customer/**", "/add_bike/**", "/update_bike/**",
                "/delete_bike/**", "/add_update_salesman/**",
                "/add_salesman/**", "/update_salesman/**", "/delete_salesman/**"};
    }

    private String[] generalUrls() {
        return new String[]{"/", "/bike/**", "/images/logo/logo2.jpg", "/images/error.png", "/service/**", "/customers_purchases/**",
                "/invoices_purchases/**", "/invoices/download/**", "/add_customer/**", "/update_customer/**", "/delete_customer/**", "/user/info", "/error"};
    }

    @Bean
    @ConditionalOnProperty(value = "spring.security.enabled", havingValue = "false")
    SecurityFilterChain securityDisabled(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );
        return http.build();
    }
}