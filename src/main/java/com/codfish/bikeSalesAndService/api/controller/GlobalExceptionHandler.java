package com.codfish.bikeSalesAndService.api.controller;

import com.codfish.bikeSalesAndService.domain.exception.ForbiddenException;
import com.codfish.bikeSalesAndService.domain.exception.NotFoundException;
import com.codfish.bikeSalesAndService.domain.exception.ProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception exception) {
        String logMessage = String.format("Other exception occurred: [%s]", exception.getMessage());
        log.error(logMessage, exception);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", exception.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNoResourceFound(NotFoundException exception) {
        String logMessage = String.format("Could not find: [%s]", exception.getMessage());
        log.error(logMessage, exception);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", exception.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(ProcessingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleProcessingException(ProcessingException exception) {
        String logMessage = String.format("Processing exception occurred: [%s]", exception.getMessage());
        log.error(logMessage, exception);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", exception.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handlerBadRequest(BindException exception) {
        String field = Optional.ofNullable(exception.getFieldError()).map(FieldError::getField).orElse(null);
        Object rejectedValue = Optional.ofNullable(exception.getFieldError()).map(FieldError::getRejectedValue).orElse(null);
        String value = rejectedValue != null ? rejectedValue.toString() : null;
        String logMessage = String.format("Bad request for field: [%s], wrong value: [%s]", field, value);
        log.error(logMessage, exception);
        
        String userMessage = String.format("Invalid value [%s] for field [%s]", value, field);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", userMessage);
        return modelAndView;
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handleForbiddenException(ForbiddenException exception) {
        String logMessage = String.format("No authorization to display the website: [%s]", exception.getMessage());
        log.error(logMessage, exception);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", exception.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView handleConstraintViolationException(DataIntegrityViolationException ex, RedirectAttributes attributes) {
        String message = "A database constraint violation has occurred. Please check your data.";

        if (ex.getMostSpecificCause().getMessage().contains("fk_invoice_customer")) {
            message = "Cannot be deleted. This client is used for invoicing.";
        }
        if (ex.getMostSpecificCause().getMessage().contains("fk_service_person_repairing_person_repairing")) {
            message = "Cannot be deleted. The repair person repaired the bike.";
        }
        if (ex.getMostSpecificCause().getMessage().contains("fk_invoice_salesman")) {
            message = "Cannot be deleted. The seller sold the bike.";
        }
        if (ex.getMostSpecificCause().getMessage().contains("fk_service_part_part")) {
            message = "Cannot be deleted. This part is being used by a service part.";
        }
        if (ex.getMostSpecificCause().getMessage().contains("fk_service_person_bike_service")) {
            message = "Cannot be deleted. This service is being used by a service person.";
        }
        attributes.addFlashAttribute("errorMessage", message);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", message);
        return modelAndView;
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ModelAndView handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException ex) {
        String logMessage = String.format("Invalid Data Access Api Usage exception occurred: [%s]", ex.getMessage());
        log.error(logMessage, ex);

        String message = "An error occurred while accessing the database.";

        if (ex.getMessage().contains("TransientObjectException") && ex.getMessage().contains("CustomerEntity")) {
            message = "Cannot be deleted. This client is used for invoicing.";
        }

        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", message);
        return modelAndView;
    }
}