package com.server;

import com.client.Transaction;
import com.exception.UnAuthorizedRequestException;

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
    public BigDecimal doWithdraw(Transaction transaction) throws UnAuthorizedRequestException {
        if (initialBalance.compareTo(transaction.getTransactionAmount()) >= 0){
           this.initialBalance = initialBalance.subtract(transaction.getTransactionAmount());
        }
        if (initialBalance.compareTo(transaction.getTransactionAmount()) < 0){
            throw new UnAuthorizedRequestException("This initial Balance is not enough.");
        }
        return getInitialBalance();
    }

    //Deposit Account-----------------------------------------------
    public BigDecimal doDeposit(Transaction transaction) throws UnAuthorizedRequestException {
        BigDecimal newInitialBalance = getInitialBalance().add(transaction.getTransactionAmount());
        if (newInitialBalance.compareTo(upperBound) <= 0){
            this.initialBalance = newInitialBalance;
        }
        if (newInitialBalance.compareTo(upperBound) > 0){
            throw new UnAuthorizedRequestException("You pass the upperBound.");
        }
        return getInitialBalance();

    }

    @Override
    public String toString() {
        return customer + customerId + initialBalance + upperBound;
    }
}
