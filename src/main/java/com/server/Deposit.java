package com.server;

import com.client.Transaction;
import com.exception.InitialBalanceLimitationException;
import com.exception.UpperBoundLimitationException;

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

    //withdraw Operation-----------------------------------
    public synchronized BigDecimal doWithdraw(Transaction transaction) throws InitialBalanceLimitationException {
        if (initialBalance.compareTo(transaction.getTransactionAmount()) >= 0){
           this.initialBalance = initialBalance.subtract(transaction.getTransactionAmount());
        }
        if (initialBalance.compareTo(transaction.getTransactionAmount()) < 0){
            throw new InitialBalanceLimitationException("This initial Balance is not enough.");
        }
        return initialBalance;
    }

    //Deposit Operation-----------------------------------------------
    public synchronized BigDecimal doDeposit(Transaction transaction) throws UpperBoundLimitationException {
        BigDecimal newInitialBalance = getInitialBalance().add(transaction.getTransactionAmount());
        if (newInitialBalance.compareTo(upperBound) <= 0){
            //System.out.println(getInitialBalance());
            this.initialBalance = newInitialBalance;
            // System.out.println(getInitialBalance());
        }
        if (newInitialBalance.compareTo(upperBound) > 0){
            throw new UpperBoundLimitationException("You pass the upperBound.");
        }
        return initialBalance;

    }

    @Override
    public String toString() {
        return customer + customerId + initialBalance + upperBound;
    }
}
