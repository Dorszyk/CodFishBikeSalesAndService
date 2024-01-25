package com.codfish.bikeSalesAndService.api.controller;

import com.codfish.bikeSalesAndService.domain.exception.ForbiddenException;
import com.codfish.bikeSalesAndService.domain.exception.NotFoundException;
import com.codfish.bikeSalesAndService.domain.exception.ProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception exception) {
        String message = String.format("Other exception occurred: [%s]", exception.getMessage());
        log.error(message, exception);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", message);
        return modelAndView;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNoResourceFound(NotFoundException exception) {
        String message = String.format("Could not find: [%s]", exception.getMessage());
        log.error(message, exception);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", message);
        return modelAndView;
    }

    @ExceptionHandler(ProcessingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleProcessingException(ProcessingException exception) {
        String message = String.format("Processing exception occurred: [%s]", exception.getMessage());
        log.error(message, exception);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", message);
        return modelAndView;
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handlerBadRequest(BindException exception) {
        String message = String.format("Bad request for field: [%s], wrong value: [%s]",
                Optional.ofNullable(exception.getFieldError()).map(FieldError::getField).orElse(null),
                Optional.ofNullable(exception.getFieldError()).map(FieldError::getRejectedValue).orElse(null));
        log.error(message, exception);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", message);
        return modelAndView;
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handleForbiddenException(ForbiddenException exception) {
        String message = String.format("No authorization to display the website: [%s]", exception.getMessage());
        log.error(message, exception);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", message);
        return modelAndView;
    }
}
