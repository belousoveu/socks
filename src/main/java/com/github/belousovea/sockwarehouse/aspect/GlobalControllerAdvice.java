package com.github.belousovea.sockwarehouse.aspect;

import com.github.belousovea.sockwarehouse.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalControllerAdvice {


    @ExceptionHandler({IllegalRequestParameterException.class,
            IllegalFilterRangeException.class,
            TooManyFilterParametersException.class,
            TooManySortingParametersException.class})
    public ResponseEntity<String> handleBadRequestException(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NotEnoughGoodsException.class)
    public ResponseEntity<String> handleNotEnoughGoodsException(RuntimeException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(FileReadingException.class)
    public ResponseEntity<String> handleFileReadingException(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ValidationErrorResponse.Violation> violations = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new ValidationErrorResponse.Violation(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(new ValidationErrorResponse(violations), HttpStatus.BAD_REQUEST);
    }
}
