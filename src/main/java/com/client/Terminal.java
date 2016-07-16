package com.client;

/**
 * Created by Dotin school 6 on 7/12/2016.
 */
public class Terminal
{
    private int terminalId;
    private String terminalType;
    private String serverIpAddress;
    private int portNumber;
    private String outLogPath;


    //getter
    public int getTerminalId() {
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

    //setter
    public void setTerminalId(int terminalId) {
        this.terminalId = terminalId;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    public void setServerIpAddress(String serverIpAddress) {
        this.serverIpAddress = serverIpAddress;
        System.out.println(this.serverIpAddress);
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public void setOutLogPath(String outLogPath) {
        this.outLogPath = outLogPath;
    }


}