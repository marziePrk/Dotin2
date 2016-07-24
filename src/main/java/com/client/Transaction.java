package com.client;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Dotin school 6 on 7/12/2016.
 */
public class Transaction implements Serializable {
    private String transactionId;
    private String transactionType;
    private BigDecimal transactionAmount;
    private String depositId;
    private static Transaction transaction;
    //????????
    private static List<Transaction> transactionList;


    //getter-----------------------------------------------
    public String getTransactionId() {
        return transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public String getDepositId() {
        return depositId;
    }

    public static List<Transaction> getTransactionList() {
        return transactionList;
    }

    public static Transaction getTransaction() {
        return transaction;
    }

    //setter--------------------------------------------------------------------------------
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setTransactionAmount(BigDecimal amountString) {
        this.transactionAmount =amountString ;
    }

    public void setDepositId(String depositId) {
        this.depositId = depositId;
    }

    public static void setTransactionList(List<Transaction> transactionList) {
        Transaction.transactionList = transactionList;
    }

    public static void setTransaction(Transaction transaction) {
        Transaction.transaction = transaction;
    }

    @Override
    public String toString() {
        return transactionId + transactionType + transactionAmount + depositId;
    }
}
