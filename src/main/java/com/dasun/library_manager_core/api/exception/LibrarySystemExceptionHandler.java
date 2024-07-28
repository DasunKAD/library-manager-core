package com.dasun.library_manager_core.api.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Log4j2
public class LibrarySystemExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String LOG_MESSAGE_TEMPLATE = "{}: {}";

    // Error messages loaded from properties file with default values
    @Value("${error.badRequest}")
    private String badRequestMessage;

    @Value("${error.notFound}")
    private String resourceNotFoundMessage;

    @Value("${error.systemError}")
    private String systemErrorMessage;

    @Value("${error.authenticationFailed}")
    private String authenticationFailedMessage;

    @Value("${error.accessDenied}")
    private String accessDeniedMessage;

    // Handles IllegalArgumentException and returns a 400 Bad Request response
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(IllegalArgumentException ex) {
        log.error(LOG_MESSAGE_TEMPLATE, badRequestMessage, ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                badRequestMessage,
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.toString()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Handles DataNotFoundException and returns a 404 Not Found response
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(DataNotFoundException ex) {
        log.error(LOG_MESSAGE_TEMPLATE, resourceNotFoundMessage, ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                resourceNotFoundMessage,
                ex.getMessage(),
                HttpStatus.NOT_FOUND.toString()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Handles generic exceptions and returns a 500 Internal Server Error response
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        log.error(LOG_MESSAGE_TEMPLATE, systemErrorMessage, ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                systemErrorMessage,
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.toString()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handles AuthenticationException and returns a 401 Unauthorized response
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
        log.error(LOG_MESSAGE_TEMPLATE, authenticationFailedMessage, ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                authenticationFailedMessage,
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED.toString()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    // Handles AccessDeniedException and returns a 403 Forbidden response
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        log.error(LOG_MESSAGE_TEMPLATE, accessDeniedMessage, ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                accessDeniedMessage,
                ex.getMessage(),
                HttpStatus.FORBIDDEN.toString()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

}
