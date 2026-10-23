package org.example.model;

public class Opay {

    private String transactionRef;
    private final int amount;
    private final String userEmail;

    private String transactionId;


    public Opay(String transactionId,  String transactionRef, int amount, String userEmail){
        this.transactionId = transactionId;
        this.transactionRef = transactionRef;
        this.amount = amount;
        this.userEmail = userEmail;

    }

    public void setTransactionId(String transactionId){
        this.transactionId = transactionId;
    }

    public String getTransactionId() {
        return transactionId;
    }


    public void setTransactionRef(String transactionRef){

        this.transactionRef = transactionRef;
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
