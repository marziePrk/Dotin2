package com.client;

import com.server.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.*;

import static com.client.XmlManager.readXml;
import static com.client.XmlManager.writeXml;

/**
 * Created by Dotin school 6 on 7/10/2016.
 */
public class ClientThread extends Thread{
    private final Logger logger=  Logger.getLogger(ClientThread.class.getName());
    Terminal terminal;

    //Handler fileHandler;
    //SimpleFormatter simpleFormatter;

    public ClientThread(String inputFilePath) throws IOException {
        terminal = readXml(inputFilePath);
        Handler fileHandler = new FileHandler("resources\\"+terminal.getOutLogPath());
        SimpleFormatter simpleFormatter = new SimpleFormatter();
        logger.addHandler(fileHandler);
        logger.setLevel(Level.INFO);
        fileHandler.setFormatter(simpleFormatter);
    }

    public  void run() {

       /* Handler fileHandler = new FileHandler(terminal.getOutLogPath());
        SimpleFormatter simpleFormatter=  new SimpleFormatter();
        logger.addHandler(fileHandler);
        logger.setLevel(Level.INFO);
        fileHandler.setFormatter(simpleFormatter);*/

        logger.info("Start terminal" + terminal.getTerminalId());
        String ipAddress = terminal.getServerIpAddress();
        int port = terminal.getPortNumber();
        for (Transaction transaction : terminal.getTransactions()) {
            //System.out.println("Connecting to " + ipAddress + " on port " + port);
            logger.info("Connecting to " + ipAddress + " on port " + port);
            try {
                Socket clientSocket = new Socket(ipAddress, port);
                //System.out.println("Just connected to " + clientSocket.getRemoteSocketAddress());
                logger.info("Just connected to " + clientSocket.getRemoteSocketAddress());

                //send request-----------------------------------------------------------------------
                ObjectOutputStream clientOutput = new ObjectOutputStream(clientSocket.getOutputStream());
                clientOutput.writeObject(transaction);
                logger.info("Send transaction " + transaction.getTransactionId() +"to server.");

                //receive response-------------------------------------------------------------------
                ObjectInputStream clientInput = new ObjectInputStream(clientSocket.getInputStream());
                Response serverResponse = (Response) clientInput.readObject();
                System.out.println("Server says " + serverResponse);
                logger.info("Response of transaction" + transaction.getTransactionId() +" is received.Server say " + serverResponse.getResponseMessage());
                writeXml(serverResponse);
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    //create output file----------------------
   /* public static void createXmlFile(Response serverResponse) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            //root element
            Element rootElement = document.createElement("Responses");
            document.appendChild(rootElement);
            //child element
            Element idElement = document.createElement("Response");
            rootElement.appendChild(idElement);
            Attr attr = document.createAttribute("id");
            attr.setValue(serverResponse.getResponseId());
            idElement.setAttributeNode(attr);

            Element balanceElement = document.createElement("newInitialBalance");
            balanceElement.appendChild(document.createTextNode(serverResponse.getResponseId()) );
            rootElement.appendChild(balanceElement);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new FileWriter(outputFilePath));
            transformer.transform(domSource, streamResult);


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }*/
}

