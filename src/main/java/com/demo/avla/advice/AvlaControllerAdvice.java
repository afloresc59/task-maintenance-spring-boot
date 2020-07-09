package com.demo.avla.advice;

import com.demo.avla.exception.ServiceException;
import com.demo.avla.model.response.ErrorResponse;
import com.demo.avla.type.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AvlaControllerAdvice {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> handleServiceException(ServiceException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setType(ErrorType.API_ERROR.name());
        errorResponse.setMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setType(ErrorType.GENERIC_ERROR.name());
        errorResponse.setMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

}
