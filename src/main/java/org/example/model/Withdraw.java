package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "withdraws")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Withdraw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String withdrawTxnId;
    private String email;

    private String accountNumber;
    private String accountName;
    private String bankName;
    private int amount;

    private boolean settled;

    public void setWithdrawTxnId(String withdrawTxnId) {
        this.withdrawTxnId = withdrawTxnId;
    }

    public String getWithdrawTxnId() {
        return withdrawTxnId;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    public String getAccountName() {
        return accountName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    public String getBankName() {
        return bankName;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    public int getAmount() {
        return amount;
    }

    public void setSettled(boolean settled) {
        this.settled = settled;
    }
    public boolean isSettled() {
        return settled;
    }
}