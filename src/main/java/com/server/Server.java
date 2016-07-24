package com.server;

/**
 * Created by Dotin school 6 on 7/23/2016.
 */
public class Server {
    private int port;
    private String outLog;

    //getter
    public int getPort() {
        return port;
    }

    public String getOutLog() {
        return outLog;
    }

    //setter
    public void setPort(int port) {
        this.port = port;
    }

    public void setOutLog(String outLog) {
        this.outLog = outLog;
    }

}