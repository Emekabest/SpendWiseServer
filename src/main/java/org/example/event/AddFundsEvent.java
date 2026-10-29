package org.example.event;

import org.example.model.Transaction;

public class AddFundsEvent {

    private Transaction transaction;

    public AddFundsEvent(Transaction transaction){
        this.transaction = transaction;

    }


    public Transaction getTransaction(){

        return transaction;
    }

}
