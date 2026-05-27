package br.com.augustojsdev.stagepass.infra.exceptions.http.handler;

import br.com.augustojsdev.stagepass.infra.exceptions.http.dtos.DefaultErrorResponse;
import br.com.augustojsdev.stagepass.infra.exceptions.http.dtos.ValidationErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@RestController
@Log4j2
public class RequestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        log.error("DTO validation error.: {}", ex.getMessage());

        Map<String, List<String>> errors = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(
                                FieldError::getDefaultMessage,
                                Collectors.toList()
                        )
                ));

        ValidationErrorResponse response = ValidationErrorResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message("Validation failed")
                .errors(errors)
                .timestamp(OffsetDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public final ResponseEntity<DefaultErrorResponse> handleResponseStatusException(
            ResponseStatusException ex,
            HttpServletRequest request) {

        log.error(ex);
        log.error("HTTP Request exception.: {}", ex.getMessage());

        DefaultErrorResponse response = DefaultErrorResponse.builder()
                .statusCode(ex.getStatusCode().value())
                .error("Request error")
                .message(ex.getMessage())
                .timestamp(OffsetDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(ex.getStatusCode().value()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<DefaultErrorResponse> handleUnexpectedErrors(
            Exception ex,
            HttpServletRequest request) {

        log.error(ex);

        DefaultErrorResponse response = DefaultErrorResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .message("An unexpected error occurred. Please try again later.")
                .timestamp(OffsetDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<DefaultErrorResponse> handleMethodArgumentTypeMismatchException(
            Exception ex,
            HttpServletRequest request) {

        log.error(ex);

        DefaultErrorResponse response = DefaultErrorResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .error("Request validation error.")
                .message(ex.getMessage())
                .timestamp(OffsetDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
