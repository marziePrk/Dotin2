package com.server;

import java.util.List;

/**
 * Created by Dotin school 6 on 7/23/2016.
 */
public class ServerInformation {
    private int port;
    private String outLog;
    private List<Deposit> deposits;

    //getter
    public int getPort() {
        return port;
    }

    public String getOutLog() {
        return outLog;
    }

    public List<Deposit> getDeposits() {
        return deposits;
    }


    //setter
    public void setPort(int port) {
        this.port = port;
    }

    public void setOutLog(String outLog) {
        this.outLog = outLog;
    }

    public void setDeposits(List<Deposit> deposits) {
        this.deposits = deposits;
    }

}