package com.client;

import java.util.List;

/**
 * Created by Dotin school 6 on 7/12/2016.
 */
public class Terminal
{
    private String terminalId;
    private String terminalType;
    private String serverIpAddress;
    private int portNumber;
    private String outLogPath;
    private List<Transaction> transactions;

    //getter...........................................
    public String getTerminalId() {
        return terminalId;
    }

    public String getTerminalType() {
        return terminalType;
    }

    public String getServerIpAddress() {
        return this.serverIpAddress;
    }

    public int getPortNumber() {
        return this.portNumber;
    }

    public String getOutLogPath() {
        return outLogPath;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }


    //setter.....................................................................
    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    public void setServerIpAddress(String serverIpAddress) {
        this.serverIpAddress = serverIpAddress;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public void setOutLogPath(String outLogPath) {
        this.outLogPath = outLogPath;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

}
