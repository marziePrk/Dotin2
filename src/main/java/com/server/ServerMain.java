package com.server;

import com.client.Transaction;
import com.exception.InvalidCustomerException;
import com.exception.UnAuthorizedRequestException;

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
 * Created by Dotin school 6 on 7/10/2016.
 */
/*public class ServerMain extends Thread
{
    private ServerSocket serverSocket;

    public ServerMain(int port) throws IOException
    {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10000);
    }

    public void run()
    {
        while(true)
        {
            try
            {
                System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();
                System.out.println("Just connected to "+ server.getRemoteSocketAddress());
                ObjectInputStream serverIn = new ObjectInputStream(server.getInputStream());
                System.out.println(serverIn.readObject());

                Transaction transaction = (Transaction) serverIn.readObject();
                Validation validation = new Validation(transaction);
                validation.setMessage();
                //validation.checkRequest(transaction);


                ObjectOutputStream serverOut = new ObjectOutputStream(server.getOutputStream());
                serverOut.writeObject("Thank you for connecting to " + server.getLocalSocketAddress() + "\nGoodbye!");
                server.close();
            }catch(SocketTimeoutException s)
            {
                System.out.println("Socket timed out!");
                break;
            }catch(IOException e)
            {
                e.printStackTrace();
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }*/

public class ServerMain {
    public static final String path = "resources\\core.json";

    //Server Main
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket;
        ServerInformation serverInfo = readServerInfo(path);
        int port = serverInfo.getPort();
        List<Deposit> depositList = readDepositsInfo(path);
        Response response = new Response();

        serverSocket = new ServerSocket(port);
        //serverSocket.setSoTimeout(10000);

        while (true) {
            Response serverResponse = null;
            System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();
            try {
                //get connection----------------------------------------------------------------
                System.out.println("Just connected to " + server.getRemoteSocketAddress());

                //receive client request-----------------------------------------------------
                ObjectInputStream serverIn = new ObjectInputStream(server.getInputStream());
                Transaction transaction = (Transaction) serverIn.readObject();

                //validation the request------------------------------------------------------
                boolean checkCustomerExist = false;
                for (Deposit deposit : depositList){
                    if (deposit.getCustomerId().equals(transaction.getDepositId())){
                        BigDecimal newBalance=null;
                        checkCustomerExist = true;
                        if (transaction.getTransactionType().equals("deposit")){
                           newBalance = deposit.doDeposit(transaction);
                           System.out.println(newBalance);
                           serverResponse = new Response(transaction , newBalance);
                        }else if (transaction.getTransactionType().equals("withdraw")){
                           newBalance = deposit.doWithdraw(transaction);
                           System.out.println(newBalance);
                            serverResponse = new Response( transaction, newBalance);
                       }
                    }
                }
                if (!checkCustomerExist){
                    serverResponse = new Response("Server can not matched id.");
                    response.makeInvalidCustomerException("can not matched id.");
                    System.out.println(serverResponse);
                }

            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");

            } catch (IOException e) {
                System.out.println("I am IO Exception in serverMain");
                e.printStackTrace();

            } catch (ClassNotFoundException e) {
                System.out.println("I am ClassNotFound exception in serverMain ");
                e.printStackTrace();
            } catch (InvalidCustomerException e) {
                e.printStackTrace();
            } catch (UnAuthorizedRequestException e) {
                e.printStackTrace();
                serverResponse= new Response(e.getMessage());
            }

            //send response--------------------------------------------------------------
            ObjectOutputStream serverOut = new ObjectOutputStream(server.getOutputStream());
            serverOut.writeObject(serverResponse );
            server.close();
        }
    }



       /* try
        {
            //rename this!!!!!
            Thread serverThread = new ServerMain(port);
            serverThread.start();

        }catch(IOException e)
        {
            System.out.print("Server main exception....");
            e.printStackTrace();
        }*/
}
