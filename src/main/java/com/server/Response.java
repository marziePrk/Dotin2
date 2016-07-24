package com.server;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Dotin school 6 on 7/23/2016.
 */
public class Response implements Serializable {
    private String responseId;
    private BigDecimal newBalance;
    private String responseMessage;
    private String responseStatus;
    private String transactionType;


    public Response() {
    }

    public Response(String responseId, BigDecimal newBalance, String responseMessage, String transactionType) {
        this.responseId = responseId;
        this.newBalance = newBalance;
        this.responseMessage = responseMessage;
        this.transactionType = transactionType;
    }


    //getter------------------------------------
    public String getResponseId() {
        return responseId;
    }

    public BigDecimal getNewBalance() {
        return newBalance;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public String getTransactionType() {
        return transactionType;
    }

    //setter------------------------------------
    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public void setNewBalance(BigDecimal newBalance) {
        this.newBalance = newBalance;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "responseId : " +responseId +
                "  newBalance : " +newBalance +
                "  response Message :"+responseMessage +
                "  transactionType :"+ transactionType;
    }
}
