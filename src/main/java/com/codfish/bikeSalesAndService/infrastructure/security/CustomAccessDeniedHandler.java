package com.codfish.bikeSalesAndService.infrastructure.security;

import com.codfish.bikeSalesAndService.domain.exception.ForbiddenException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) {
        try {
            throw new ForbiddenException("No authorization to display the website.");
        } catch (ForbiddenException e) {
            throw new RuntimeException(e);
        }
    }
}
