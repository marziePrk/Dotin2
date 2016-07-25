package com.server;

import com.client.Transaction;
import com.exception.InvalidCustomerException;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Dotin school 6 on 7/23/2016.
 */
public class Response implements Serializable {
    private String responseId;
    private BigDecimal newInitialBalance;
    private String responseMessage;
    private String transactionType;
    private Response response;



    public Response( String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public Response() {

    }


    public Response(Transaction transaction , BigDecimal newBalance){

            this.responseId =transaction.getTransactionId();
            this.newInitialBalance = newBalance;
            this.responseMessage = "The operation was successful.";
            this.transactionType = transaction.getTransactionType();
    }
    public Response makeSuccessfulResponse(Transaction transaction , BigDecimal newBalance){
        this.responseId =transaction.getTransactionId();
        this.newInitialBalance = newBalance;
        this.responseMessage = "The operation was successful.";
        this.transactionType = transaction.getTransactionType();
        return response;

    }
    public void makeInvalidCustomerException(String responseMessage) throws InvalidCustomerException {
       throw new InvalidCustomerException(responseMessage);
    }


    //getter------------------------------------
    public String getResponseId() {
        return responseId;
    }

    public BigDecimal getNewBalance() {
        return newInitialBalance;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public Response getResponse() {
        return response;
    }

    //setter------------------------------------
    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public void setNewBalance(BigDecimal newBalance) {
        this.newInitialBalance = newBalance;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "responseId : " +responseId +
                "  newBalance : " +newInitialBalance +
                "  response Message :"+responseMessage +
                "  transactionType :"+ transactionType;
    }
}
