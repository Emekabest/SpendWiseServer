package org.example.model;

public class Opay {

    private final String transactionRef;
    private final int amount;
    private final String userEmail;

    public Opay(String transactionRef, int amount, String userEmail){
        this.transactionRef = transactionRef;
        this.amount = amount;
        this.userEmail = userEmail;

    }


    public String getTransactionRef() {
        return transactionRef;
    }

    public int getAmount() {
        return amount;
    }

    public String getUserEmail(){

        return userEmail;
    }
}
