package com.client;

import java.io.*;
import java.net.Socket;

/**
 * Created by Dotin school 6 on 7/10/2016.
 */
public class ClientMain
{
    public static void main(String [] args) {
        String path = "resources\\terminal.xml";
        XmlReader xmlReader = new XmlReader();
        xmlReader.readXml(path);
        String ipAddress = xmlReader.terminal.getServerIpAddress();
        int port = xmlReader.terminal.getPortNumber();
        System.out.println("Connecting to " + ipAddress + " on port " + port);
        for (int counter = 0; counter < xmlReader.transactionList.size() ; counter ++) {
            try {
                Socket clientSocket = new Socket(ipAddress, port);
                System.out.println("Just connected to " + clientSocket.getRemoteSocketAddress());
                ObjectOutputStream clientOutput = new ObjectOutputStream(clientSocket.getOutputStream());
                Transaction transaction = xmlReader.transactionList.get(counter);
                clientOutput.writeObject(transaction);
                ObjectInputStream clientInput = new ObjectInputStream(clientSocket.getInputStream());
                System.out.println("Server says " + clientInput.readObject());
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
