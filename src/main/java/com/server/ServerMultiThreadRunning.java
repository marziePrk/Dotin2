package com.server;

import com.client.Transaction;
import com.exception.InitialBalanceLimitationException;
import com.exception.InvalidCustomerException;
import com.exception.InvalidDepositTypeException;
import com.exception.UpperBoundLimitationException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static com.server.JsonParser.readDepositsInfo;
import static com.server.JsonParser.readServerInfo;

/**
 * Created by Dotin school 6 on 7/26/2016.
 */
public class ServerMultiThreadRunning extends Thread {
    private static final String jsonPath = "resources\\core.json";
    private static final Logger logger = Logger.getLogger(ServerMultiThreadRunning.class.getName());
    ServerInformation serverInfo;
    ObjectInputStream serverIn;
    ObjectOutputStream serverOut;
    Response serverResponse;
    Socket server;
    List<Deposit> depositList;

    public ServerMultiThreadRunning(Socket server, List<Deposit> depositList, ServerInformation serverInfo) throws IOException {
        this.server = server;
        this.depositList = depositList;
        this.serverInfo = serverInfo;
    }

    //main of ServerMultiThreadingRunning.......................
    public static void main(String[] args) {
        ServerInformation serverInfo = readServerInfo(jsonPath);
        int port = serverInfo.getPort();
        List<Deposit> depositList = readDepositsInfo(jsonPath);

        try {
            FileHandler fileHandler = new FileHandler("resources\\"+serverInfo.getOutLog());
            SimpleFormatter simpleFormatter=  new SimpleFormatter();
            logger.addHandler(fileHandler);
            logger.setLevel(Level.INFO);
            fileHandler.setFormatter(simpleFormatter);
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
                logger.info("Waiting for client on port " + serverSocket.getLocalPort() + "...");
                Socket socket = serverSocket.accept();
                Thread serverThread = new ServerMultiThreadRunning(socket , depositList , serverInfo);
                System.out.println("Just connected to " + socket.getRemoteSocketAddress());
                logger.info("Just connected to " + socket.getRemoteSocketAddress());
                serverThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.warning("IO Exception in ServerMultiThreadRunning Class.");
        }
    }

    public void run() {

        try {
            //receive client request..................................
            serverIn = new ObjectInputStream(server.getInputStream());
            Transaction transaction = (Transaction) serverIn.readObject();
            logger.info("receive client request.\n Transaction Id =" +transaction.getTransactionId()+"\n Terminal Id ="+transaction.getTerminalId());

            //check request validation.................................
            serverResponse = checkValidation(transaction, depositList);
            logger.info("check request validation of Terminal"+transaction.getTerminalId()+"\n Transaction Id ="+transaction.getTransactionId());

            //send response.............................................
            //OutputStream outputStream = server.getOutputStream();
            serverOut = new ObjectOutputStream(server.getOutputStream());
            serverOut.writeObject(serverResponse);
            logger.info("sent response of Terminal "+serverResponse.getTerminalId());
            server.close();

        } catch (SocketTimeoutException s) {
            System.out.println("Socket timed out!");
            logger.warning("Socket timed out!");

        } catch (IOException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    //check request validation...........................................................
    public Response checkValidation(Transaction transaction, List<Deposit> depositList) {
        boolean checkCustomerExist = false;

        try {
            for (Deposit deposit : depositList) {
                if (deposit.getCustomerId().equals(transaction.getDepositId())) {
                    checkCustomerExist = true;
                    if (transaction.getTransactionType().equals("deposit")) {
                        BigDecimal newBalance = deposit.doDeposit(transaction);
                        System.out.println(newBalance);
                        serverResponse = new Response(transaction, newBalance);
                    } else if (transaction.getTransactionType().equals("withdraw")) {
                        BigDecimal newBalance = deposit.doWithdraw(transaction);
                        System.out.println(newBalance);
                        serverResponse = new Response(transaction, newBalance);
                    }else
                        throw new InvalidDepositTypeException("This deposit type is not define!") ;
                }
            }
            if (!checkCustomerExist) {
                throw new InvalidCustomerException("Invalid Customer ID...");
            }
        } catch (InitialBalanceLimitationException e) {
            e.printStackTrace();
            logger.warning(e.getMessage());
            serverResponse = new Response(e.getMessage());
        } catch (UpperBoundLimitationException e) {
            e.printStackTrace();
            logger.warning(e.getMessage());
            serverResponse = new Response(e.getMessage());
        } catch (InvalidDepositTypeException e) {
            e.printStackTrace();
            logger.warning(e.getMessage());
            serverResponse = new Response(e.getMessage());
        } catch (InvalidCustomerException e) {
            e.printStackTrace();
            logger.warning(e.getMessage());
            serverResponse = new Response(e.getMessage());
        }
        return serverResponse;
    }
}