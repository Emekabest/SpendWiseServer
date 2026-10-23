package org.example.model;

public class Opay {

    private String transactionRef;
    private final int amount;
    private final String userEmail;

    private String paymentId;


    public Opay(String paymentId, String transactionRef, int amount, String userEmail){
        this.paymentId = paymentId;
        this.transactionRef = transactionRef;
        this.amount = amount;
        this.userEmail = userEmail;

    }

    public void setPaymentId(String paymentId){
        this.paymentId = paymentId;
    }

    public String getPaymentId() {
        return paymentId;
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
