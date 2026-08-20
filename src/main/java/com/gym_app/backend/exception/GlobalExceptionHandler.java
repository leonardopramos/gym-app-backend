package com.gym_app.backend.exception;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.stream.Collectors;
import java.util.Map;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<?> notFound(ResourceNotFoundException e) { return response(HttpStatus.NOT_FOUND, e.getMessage()); }
    @ExceptionHandler({BusinessException.class, IllegalArgumentException.class})
    ResponseEntity<?> business(RuntimeException e) { return response(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage()); }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<?> validation(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream().map(x -> x.getField()+": "+x.getDefaultMessage()).collect(Collectors.joining(", "));
        return response(HttpStatus.BAD_REQUEST, message);
    }
    @ExceptionHandler(Exception.class)
    ResponseEntity<?> generic(Exception e) { return response(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor"); }
    private ResponseEntity<?> response(HttpStatus status, String message) { return ResponseEntity.status(status).body(Map.of("timestamp", Instant.now(), "status", status.value(), "message", message)); }
}
