package org.example.event;

import org.example.model.Withdraw;

public class WithdrawEvent {

    private final Withdraw withdraw;

    public WithdrawEvent(Withdraw withdraw){
        this.withdraw = withdraw;

    }

    public Withdraw getWithdraw() {
        return withdraw;
    }
}
