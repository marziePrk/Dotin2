package com.client;

import com.server.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static com.client.Terminal.getTerminal;
import static com.client.Transaction.getTransactionList;
import static com.client.XmlReader.readXml;

/**
 * Created by Dotin school 6 on 7/10/2016.
 */
public class ClientMain
{
    public static void main(String [] args) {
        String path = "resources\\terminal.xml";
        readXml(path);
        String ipAddress = getTerminal().getServerIpAddress();
        int port = getTerminal().getPortNumber();
        System.out.println("Connecting to " + ipAddress + " on port " + port);
        for (int counter = 0; counter < getTransactionList().size() ; counter ++) {
            try {
                Socket clientSocket = new Socket(ipAddress, port);
                System.out.println("Just connected to " + clientSocket.getRemoteSocketAddress());

                //send request-----------------------------------------------------------------------
                ObjectOutputStream clientOutput = new ObjectOutputStream(clientSocket.getOutputStream());
                Transaction transaction = getTransactionList().get(counter);
                clientOutput.writeObject(transaction);

                //receive response-------------------------------------------------------------------
                ObjectInputStream clientInput = new ObjectInputStream(clientSocket.getInputStream());
                Response serverResponse = (Response) clientInput.readObject();
                System.out.println("Server says " +serverResponse);
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
