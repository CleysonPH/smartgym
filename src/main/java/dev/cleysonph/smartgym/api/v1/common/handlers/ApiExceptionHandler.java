package dev.cleysonph.smartgym.api.v1.common.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dev.cleysonph.smartgym.api.v1.common.dtos.ErrorResponse;
import dev.cleysonph.smartgym.core.exceptions.ModelNotFoundException;
import dev.cleysonph.smartgym.core.service.datetime.DateTimeService;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {

    private final DateTimeService dateTimeService;

    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleModelNotFoundException(ModelNotFoundException exception) {
        var errorResponse = ErrorResponse.builder()
            .message(exception.getMessage())
            .timestamp(dateTimeService.utcNow())
            .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    
}
