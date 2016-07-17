package com.client;


import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Dotin school 6 on 7/12/2016.
 */
public class Transaction implements Serializable {
    private int transactionId;
    private String transactionType;
    private BigDecimal transactionAmount;
    private int depositId;
    
    //getter
    public int getTransactionId() {
        return transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public int getDepositId() {
        return depositId;
    }

    //setter
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setTransactionAmount(BigDecimal amountString) {
        this.transactionAmount =amountString ;
    }

    public void setDepositId(int depositId) {
        this.depositId = depositId;
    }
}
