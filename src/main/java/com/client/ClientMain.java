package com.client;

import com.server.Response;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static com.client.XmlParser.readXml;

/**
 * Created by Dotin school 6 on 7/10/2016.
 */
public class ClientMain {

    public  static String inputFilePath = "resources\\terminal.xml";
    public static String outputFilePath = "resources\\response.xml";

    public static void main(String[] args) {
        Terminal terminal = readXml(inputFilePath);
        String ipAddress = terminal.getServerIpAddress();
        int port = terminal.getPortNumber();
        System.out.println("Connecting to " + ipAddress + " on port " + port);
        for (Transaction transaction : terminal.getTransactions()) {
            try {
                Socket clientSocket = new Socket(ipAddress, port);
                System.out.println("Just connected to " + clientSocket.getRemoteSocketAddress());

                //send request-----------------------------------------------------------------------
                ObjectOutputStream clientOutput = new ObjectOutputStream(clientSocket.getOutputStream());
                //Transaction transaction = terminal.getTransactions().get(counter);
                clientOutput.writeObject(transaction);

                //receive response-------------------------------------------------------------------
                ObjectInputStream clientInput = new ObjectInputStream(clientSocket.getInputStream());
                Response serverResponse = (Response) clientInput.readObject();
                createXmlFile(serverResponse);
                System.out.println("Server says " + serverResponse);
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //create output file----------------------
    public static void createXmlFile(Response serverResponse) {
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

          /*  Element balanceElement = document.createElement("newInitialBalance");
            balanceElement.appendChild(document.createTextNode(serverResponse.getResponseId()) );
            rootElement.appendChild(balanceElement);*/

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(outputFilePath);
            transformer.transform(domSource, streamResult);


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }


    }

}

