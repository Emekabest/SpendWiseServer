package org.example.exception;

public class BudgetUsageNotFoundException extends RuntimeException {

    public BudgetUsageNotFoundException(String message) {

        super(message);
    }
}
