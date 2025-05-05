package org.alouastudios.receiptprocessor.exception;

// A custom receipt not found exception for better semantics
public class ReceiptNotFoundException extends RuntimeException {

    public ReceiptNotFoundException(String message) {
        super(message);
    }
}
