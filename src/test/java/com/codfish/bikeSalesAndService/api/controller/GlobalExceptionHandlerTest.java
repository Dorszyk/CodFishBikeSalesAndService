package com.codfish.bikeSalesAndService.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void shouldHandleInvalidDataAccessApiUsageExceptionForCustomerEntity() {
        // given
        String errorMessage = "org.hibernate.TransientObjectException: persistent instance references an unsaved transient instance of 'com.codfish.bikeSalesAndService.infrastructure.database.entity.CustomerEntity' (save the transient instance before flushing)";
        InvalidDataAccessApiUsageException exception = new InvalidDataAccessApiUsageException(errorMessage);

        // when
        ModelAndView modelAndView = exceptionHandler.handleInvalidDataAccessApiUsageException(exception);

        // then
        assertEquals("error", modelAndView.getViewName());
        assertEquals("Cannot be deleted. This client is used for invoicing.", modelAndView.getModel().get("errorMessage"));
    }

    @Test
    void shouldHandleOtherInvalidDataAccessApiUsageException() {
        // given
        String errorMessage = "Some other data access error";
        InvalidDataAccessApiUsageException exception = new InvalidDataAccessApiUsageException(errorMessage);

        // when
        ModelAndView modelAndView = exceptionHandler.handleInvalidDataAccessApiUsageException(exception);

        // then
        assertEquals("error", modelAndView.getViewName());
        assertEquals("An error occurred while accessing the database.", modelAndView.getModel().get("errorMessage"));
    }
}
