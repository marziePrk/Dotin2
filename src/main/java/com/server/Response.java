package com.server;

import com.client.Transaction;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Dotin school 6 on 7/23/2016.
 */
public class Response implements Serializable {
    private String responseId;
    private String newInitialBalance;
    private String responseMessage;
    private String transactionType;
    private String terminalId;
    private String customerId;

    //Constructor for unsuccessful operation..........................................
    public Response(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    //default Constructor...........................................
    public Response() {    }

    //Constructor for successful operation..........................
    public Response(Transaction transaction, BigDecimal newBalance) {

        this.responseId = transaction.getTransactionId();
        this.newInitialBalance = String.valueOf(newBalance);
        this.responseMessage = "The operation was successful.";
        this.transactionType = transaction.getTransactionType();
        this.terminalId = transaction.getTerminalId();
        this.customerId = transaction.getDepositId();
    }

    //getter............................................
    public String getResponseId() {
        return responseId;
    }

    public String getNewBalance() {
        return newInitialBalance;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public String getCustomerId() {
        return customerId;
    }

    //setter.....................................................................
    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public void setNewBalance(String newBalance) {
        this.newInitialBalance = newBalance;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    //toString........................................................
    @Override
    public String toString() {
        return "responseId : " + responseId +
                "  newBalance : " + newInitialBalance +
                "  response Message :" + responseMessage +
                "  transactionType :" + transactionType +
                "  terminal id :" + terminalId +
                "  customerId :" + customerId;
    }

    /* public Response makeSuccessfulResponse(Transaction transaction , BigDecimal newBalance){
         this.responseId =transaction.getTransactionId();
         this.newInitialBalance = newBalance;
         this.responseMessage = "The operation was successful.";
         this.transactionType = transaction.getTransactionType();
         return response;

     }*/
   /* public void makeInvalidCustomerException(String responseMessage) throws InvalidCustomerException {
       throw new InvalidCustomerException(responseMessage);
    }*/

}
