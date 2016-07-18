package com.server;

import com.client.Transaction;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import static com.server.JsonParserClass.getPort;

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

public class ServerMain{

    //Server Main
    public static void main(String [] args) throws IOException
    {
        ServerSocket serverSocket;
        String path = "resources\\core.json";

        JsonParserClass jsonParserClass = new JsonParserClass();
        jsonParserClass.readJson(path);
        int port =getPort();

                serverSocket = new ServerSocket(port);
                serverSocket.setSoTimeout(10000);

        while(true)
        {
            try
            {
                System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();
                System.out.println("Just connected to "+ server.getRemoteSocketAddress());
                ObjectInputStream serverIn = new ObjectInputStream(server.getInputStream());
                //System.out.println(serverIn.readObject());

                Transaction transaction = (Transaction) serverIn.readObject();
                System.out.println(transaction);
                Validation validation = new Validation();
                validation.checkRequest(transaction);


                ObjectOutputStream serverOut = new ObjectOutputStream(server.getOutputStream());
                serverOut.writeObject("Thank you for connecting to " + server.getLocalSocketAddress() + "\nGoodbye!");
                server.close();
            }catch(SocketTimeoutException s)
            {
                System.out.println("Socket timed out!");
                break;
            }catch(IOException e)
            {
                System.out.println("I am IO Exception in serverMain");
                e.printStackTrace();
                break;
            } catch (ClassNotFoundException e) {
                System.out.println("I am ClassNotFound exception in serverMain ");
                e.printStackTrace();
            }
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
