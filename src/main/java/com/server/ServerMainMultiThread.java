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
import java.net.SocketTimeoutException;
import java.util.List;

import static com.server.JsonParser.readDepositsInfo;
import static com.server.JsonParser.readServerInfo;

/**
 * Created by Dotin school 6 on 7/26/2016.
 */
public class ServerMainMultiThread extends Thread {
    public static final String jsonPath = "resources\\core.json";
    ServerInformation serverInfo;
    ObjectInputStream serverIn;
    ObjectOutputStream serverOut;
    Response serverResponse;
    Socket server;
    List<Deposit> depositList;



    public ServerMainMultiThread(Socket server, List<Deposit> depositList, ServerInformation serverInfo) throws IOException {
        this.server = server;
        this.depositList = depositList;
        this.serverInfo = serverInfo;

        //serverSocket.setSoTimeout(10000);
    }

    public void run() {

        try {


            //receive client request..................................
            serverIn = new ObjectInputStream(server.getInputStream());
            Transaction transaction = (Transaction) serverIn.readObject();

            //condition out of the loop................................


            //check request validation.................................
            serverResponse = checkValidation(transaction, depositList);

            //send response.............................................
            //OutputStream outputStream = server.getOutputStream();
            serverOut = new ObjectOutputStream(server.getOutputStream());
            serverOut.writeObject(serverResponse);
            server.close();

        } catch (SocketTimeoutException s) {
            System.out.println("Socket timed out!");

        } catch (IOException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public Response checkValidation(Transaction transaction, List<Deposit> depositList) {
        boolean checkCustomerExist = false;

        try {
            for (Deposit deposit : depositList) {
                if (deposit.getCustomerId().equals(transaction.getDepositId())) {
                    BigDecimal newBalance = null;
                    checkCustomerExist = true;
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
            if (!checkCustomerExist) {
                serverResponse = new Response("Invalid Customer ID , repeat again...");
                System.out.println(serverResponse);
            }
        } catch (InitialBalanceLimitationException e) {
            e.printStackTrace();
            serverResponse = new Response(e.getMessage());
        } catch (UpperBoundLimitationException e) {
            e.printStackTrace();
        }
        return serverResponse;
    }

    public static void main(String[] args) {
        ServerInformation serverInfo = readServerInfo(jsonPath);
        int port = serverInfo.getPort();
        List<Deposit> depositList = readDepositsInfo(jsonPath);

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();
                Thread serverThread = new ServerMainMultiThread(server , depositList , serverInfo);
                System.out.println("Just connected to " + server.getRemoteSocketAddress());
                serverThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}