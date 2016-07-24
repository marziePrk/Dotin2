package com.server;

import com.client.Transaction;

import java.math.BigDecimal;

/**
 * Created by Dotin school 6 on 7/17/2016.
 */
public class Deposit{
    private String customer;
    private String customerId;
    private BigDecimal initialBalance;
    private BigDecimal upperBound;


    //getter-------------------------------------------------

    public String getCustomerId() {
        return customerId;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public BigDecimal getUpperBound() {
        return upperBound;
    }

    public String getCustomer() {
        return customer;
    }

    //setter-----------------------------------------------------
    public void setId(String customerId) {
        this.customerId = customerId;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }

    public void setUpperBound(BigDecimal upperBound) {
        this.upperBound = upperBound;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    //withdraw from bank account-----------------------------------
    public BigDecimal doWithdraw(Transaction transaction, Deposit deposit) {
        System.out.println("I am withdraw");
        setInitialBalance(deposit.getInitialBalance().subtract(transaction.getTransactionAmount()));
        System.out.println(getInitialBalance());
        return getInitialBalance();
    }

    //Deposit Account-----------------------------------------------
    public BigDecimal doDeposit(Transaction transaction, Deposit deposit) {
        setInitialBalance(deposit.getInitialBalance().add(transaction.getTransactionAmount()));
        System.out.println(getInitialBalance());
        return getInitialBalance();

    }

    @Override
    public String toString() {
        return customer + customerId + initialBalance + upperBound;
    }
}
