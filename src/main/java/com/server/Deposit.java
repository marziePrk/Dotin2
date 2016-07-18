package com.server;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Dotin school 6 on 7/17/2016.
 */
public class Deposit implements Serializable{
    private String customer;
    private String customerId;
    private BigDecimal initialBalance;
    private BigDecimal upperBound;

    //getter
    public String getCustomer() {
        return customer;
    }

    public String getCustomerId() {
        return customerId;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public BigDecimal getUpperBound() {
        return upperBound;
    }

    //setter
    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setId(String customerId) {
        this.customerId = customerId;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }

    public void setUpperBound(BigDecimal upperBound) {
        this.upperBound = upperBound;
    }
}
