package com.mobifone.signature.exception;

import com.mobifone.signature.model.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.Timestamp;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = {BadRequestException.class})
    protected ResponseEntity<Object> handleConflict(
            BadRequestException ex, WebRequest request) {
        Response response = Response
                .builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .status(HttpStatus.BAD_REQUEST)
                .message(ex.getMessage())
                .path(request.getContextPath())
                .build();

        return handleExceptionInternal(ex, response,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}