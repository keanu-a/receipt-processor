package org.alouastudios.receiptprocessor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            {MethodArgumentNotValidException.class, NoResourceFoundException.class, InvalidDateTimeFormatException.class}
    )
    public ResponseEntity<Map<String, String>> handleInvalidReceipt(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of("message","The receipt is invalid.")
        );
    }

    @ExceptionHandler(ReceiptNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleReceiptNotFoundException(ReceiptNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("message","No receipt found for that ID.")
        );
    }
}
