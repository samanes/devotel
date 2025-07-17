package com.devotel.shared.exception;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ProblemDetail handleNotFound(ResourceNotFoundException ex, HttpServletRequest req) {
    var pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    pd.setTitle("Resource Not Found");
    pd.setInstance(URI.create(req.getRequestURI()));
    pd.setProperty("timestamp", OffsetDateTime.now());
    return pd;
  }

  @ExceptionHandler(ExternalServiceException.class)
  public ProblemDetail handleExternal(ExternalServiceException ex, HttpServletRequest req) {
    ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_GATEWAY, ex.getMessage());
    pd.setTitle("External Service Error");
    pd.setInstance(URI.create(req.getRequestURI()));
    pd.setProperty("timestamp", OffsetDateTime.now());
    return pd;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ProblemDetail handleValidation(
      MethodArgumentNotValidException ex, HttpServletRequest req) {
    String errors =
        ex.getBindingResult().getFieldErrors().stream()
            .map(FieldError::getDefaultMessage)
            .collect(Collectors.joining("; "));
    ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errors);
    pd.setTitle("Validation Failed");
    pd.setInstance(URI.create(req.getRequestURI()));
    pd.setProperty("timestamp", OffsetDateTime.now());
    return pd;
  }

  @ExceptionHandler(Exception.class)
  public ProblemDetail handleAll(Exception ex, HttpServletRequest req) {
    ProblemDetail pd =
        ProblemDetail.forStatusAndDetail(
            HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
    pd.setTitle("Internal Error");
    pd.setInstance(URI.create(req.getRequestURI()));
    pd.setProperty("timestamp", OffsetDateTime.now());
    return pd;
  }

}