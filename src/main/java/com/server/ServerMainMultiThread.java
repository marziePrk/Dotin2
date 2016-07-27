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
    ServerSocket serverSocket;
    ObjectInputStream serverIn;
    ObjectOutputStream serverOut;
    Response serverResponse;


    public ServerMainMultiThread() throws IOException {
        ServerInformation serverInfo = readServerInfo(jsonPath);
        int port = serverInfo.getPort();
        serverSocket = new ServerSocket(port);
        //serverSocket.setSoTimeout(10000);
    }

    public void run() {
        List<Deposit> depositList = readDepositsInfo(jsonPath);
        while (true) {
            try {
                System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();
                //get connection...........................................................
                System.out.println("Just connected to " + server.getRemoteSocketAddress());

                //receive client request..................................
                serverIn = new ObjectInputStream(server.getInputStream());
                Transaction transaction = (Transaction) serverIn.readObject();

                //condition out of the loop................................
                if (transaction == null)
                    break;

                //check request validation.................................
                serverResponse = checkValidation(transaction, depositList);

                //send response.............................................
                //OutputStream outputStream = server.getOutputStream();
                serverOut = new ObjectOutputStream(server.getOutputStream());
                serverOut.writeObject(serverResponse);
                server.close();

            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
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

        try {
            Thread serverThread = new ServerMainMultiThread();
            serverThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}