package com.codfish.bikeSalesAndService.infrastructure.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception)
            throws IOException {
        String message = String.format("No authorization to display the website: [%s]", exception.getMessage());
        log.error(message, exception);
        if (message.contains("No authorization to display the website: [Access Denied]")) {
            message = "No authorization to display the website.";
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("errorMessage", message);
        request.setAttribute("errorMessage", message);
        try {
            request.getRequestDispatcher("/error").forward(request, response);
        } catch (ServletException e) {
            log.error("Error occurred while forwarding to error view", e);
        }
    }
}