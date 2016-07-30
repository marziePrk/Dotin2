package com.server;

import com.client.Transaction;
import com.exception.InitialBalanceLimitationException;
import com.exception.UpperBoundLimitationException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import static com.server.JsonParser.readDepositsInfo;
import static com.server.JsonParser.readServerInfo;


/**
 * Created by Dotin school 6 on 7/10/2016.
 */

public class ServerSingleThread {
    public static final String path = "resources\\core.json";
    public static Response serverResponse;


    //Server Main
    public static void main(String[] args) {
        ServerSocket serverSocket;
        ServerInformation serverInfo = readServerInfo(path);
        int port = serverInfo.getPort();
        List<Deposit> depositList = readDepositsInfo(path);
        Response response = new Response();
        ObjectInputStream serverIn;
        ObjectOutputStream serverOut;


        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                //serverSocket.setSoTimeout(10000);
                System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();
                //get connection----------------------------------------------------------------
                System.out.println("Just connected to " + server.getRemoteSocketAddress());

                //receive client request-----------------------------------------------------
                serverIn = new ObjectInputStream(server.getInputStream());
                Transaction transaction = (Transaction) serverIn.readObject();

                if (transaction == null)
                    break;

                //validation the request------------------------------------------------------
                serverResponse = checkValidation(transaction , depositList);
                //send response--------------------------------------------------------------
                serverOut = new ObjectOutputStream(server.getOutputStream());
                serverOut.writeObject(serverResponse);
                server.close();

            }
        } catch (IOException e) {
            System.out.println("I am IO Exception in serverMain");
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            System.out.println("I am ClassNotFound exception in serverMain ");
            e.printStackTrace();
        }


    }
    public static Response checkValidation( Transaction transaction ,List<Deposit> depositList){
        boolean checkCustomerExist = false;
        try {
            for (Deposit deposit : depositList) {
                if (deposit.getCustomerId().equals(transaction.getDepositId())) {
                    BigDecimal newBalance = null;
                    //checkCustomerExist = true;
                    if (transaction.getTransactionType().equals("deposit")) {
                        newBalance = deposit.doDeposit(transaction);
                        System.out.println(newBalance);
                        serverResponse = new Response(transaction, newBalance);
                    } else if (transaction.getTransactionType().equals("withdraw")) {
                        newBalance = deposit.doWithdraw(transaction);
                        System.out.println(newBalance);
                        serverResponse = new Response(transaction, newBalance);
                    }
                }
            }
           /* if (!checkCustomerExist) {
                serverResponse = new Response("Server can not matched id.");
                //response.makeInvalidCustomerException("can not matched id.");
                System.out.println(serverResponse);
            }*/
        }
        catch (InitialBalanceLimitationException e) {
            e.printStackTrace();
            serverResponse = new Response(e.getMessage());
        } catch (UpperBoundLimitationException e) {
            e.printStackTrace();
        }

        return  serverResponse;
    }
}