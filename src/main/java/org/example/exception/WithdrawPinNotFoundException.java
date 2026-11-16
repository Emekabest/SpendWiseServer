package org.example.exception;

public class WithdrawPinNotFoundException extends RuntimeException {
    public WithdrawPinNotFoundException(String message) {
        super(message);
    }
}
