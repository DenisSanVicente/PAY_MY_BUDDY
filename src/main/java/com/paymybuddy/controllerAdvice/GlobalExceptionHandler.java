package com.paymybuddy.controllerAdvice;

import com.paymybuddy.exception.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.InvalidClassException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class,
    InvalidClassException.class,
    InsufficientBalanceException.class,
    EmailAlreadyExistsException.class,
    ConnectionAlreadyExistsException.class,
    AccountNotFoundException.class})
    public String handleBusinessException(RuntimeException exception, Model model) {
        model.addAttribute("errorMessage", exception.getMessage());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception exception, Model model) {
        model.addAttribute("errorMessage", "Une erreur innatendue est survenue");
        return "error";
    }
}
