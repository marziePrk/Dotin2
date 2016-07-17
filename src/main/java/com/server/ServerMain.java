package com.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Created by Dotin school 6 on 7/10/2016.
 */
public class ServerMain extends Thread
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
                System.out.println("Waiting for client on port " +
                        serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();
                System.out.println("Just connected to "+ server.getRemoteSocketAddress());
                ObjectInputStream serverIn = new ObjectInputStream(server.getInputStream());
                System.out.println(serverIn.readObject());
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
    }

    //Server Main
    public static void main(String [] args)
    {
        String path = "resources\\core.json";
        JsonParserClass jsonParserClass = new JsonParserClass();
        jsonParserClass.readJson(path);
        int port =jsonParserClass.getPort();
        try
        {
            Thread t = new ServerMain(port);
            t.start();
        }catch(IOException e)
        {
            System.out.print("it's me...");
            e.printStackTrace();
        }
    }
}