package dev.cleysonph.smartgym.api.v1.common.handlers;

import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;

import dev.cleysonph.smartgym.api.v1.common.dtos.ErrorResponse;
import dev.cleysonph.smartgym.api.v1.common.dtos.ValidationErrorResponse;
import dev.cleysonph.smartgym.core.exceptions.ModelNotFoundException;
import dev.cleysonph.smartgym.core.services.datetime.DateTimeService;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final DateTimeService dateTimeService;
    private final SnakeCaseStrategy snakeCaseStrategy = new SnakeCaseStrategy();

    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleModelNotFoundException(ModelNotFoundException exception) {
        var errorResponse = ErrorResponse.builder()
            .message(exception.getMessage())
            .timestamp(dateTimeService.utcNow())
            .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @Override
    @Nullable
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers, 
        HttpStatusCode status, 
        WebRequest request
    ) {
        var errors = ex.getBindingResult().getFieldErrors().stream()
            .collect(Collectors.groupingBy(
                fieldError -> snakeCaseStrategy.translate(fieldError.getField()),
                Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
            ));
        var body = ValidationErrorResponse.builder()
            .message("Validation failed")
            .timestamp(dateTimeService.utcNow())
            .errors(errors)
            .build();
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    
}
