package org.example.event;

import org.example.model.Transaction;

public class WithdrawEvent {

    private final Transaction transaction;

    public WithdrawEvent(Transaction transaction){

        this.transaction = transaction;

    }

    public Transaction getTransaction(){

        return transaction;
    }
}
