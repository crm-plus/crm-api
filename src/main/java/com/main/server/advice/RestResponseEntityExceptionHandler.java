package com.main.server.advice;

import com.main.server.dto.ExceptionResponse;
import com.main.server.exception.ResourceNotFoundException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    protected ResponseEntity<ExceptionResponse> handleConflict(ResourceNotFoundException ex, HttpServletRequest request) {
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), request.getPathInfo());
        return new ResponseEntity<>(response,
                Objects.requireNonNull(
                        AnnotationUtils.findAnnotation(
                                ResourceNotFoundException.class,
                                ResponseStatus.class)
                ).value()
        );
    }

    @ExceptionHandler(value = {AuthenticationException.class})
    protected ResponseEntity<ExceptionResponse> handleAuthException(AuthenticationException ex, HttpServletRequest request) {
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), request.getPathInfo());
        return new ResponseEntity<>(response,
                Objects.requireNonNull(
                        AnnotationUtils.findAnnotation(
                                AuthenticationException.class,
                                ResponseStatus.class)
                ).value()
        );
    }
}
