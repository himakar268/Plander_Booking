package com.mdx.xyphose.usermgmt.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler({SQLException.class, SQLIntegrityConstraintViolationException.class, UserNotFoundException.class})
    public ResponseEntity<ErrorMessage> handleSQLException(Exception ex, HttpServletRequest request)
    {
          ErrorMessage errorMessage= getErrorMessage(ex);
          return ResponseEntity.badRequest().body(errorMessage);
    }

    public ErrorMessage getErrorMessage(Exception ex)
    {
        ErrorMessage message = new ErrorMessage();
        message.setStatus(HttpStatus.BAD_REQUEST.value());
        message.setMessage(ex.getMessage());
        message.setTimeStamp(LocalDateTime.now());
        return message;
    }
}
