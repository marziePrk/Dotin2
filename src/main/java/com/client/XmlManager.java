package com.client;


import com.server.Response;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Dotin school 6 on 7/12/2016.
 */
public class XmlManager {
    //Parse input xml file........................
    public static Terminal readXml(String path) {
        Terminal terminal = new Terminal();
        try {
            File xmlFile = new File(path);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            //document.getDocumentElement().normalize();

            //.........................terminal...................................
            NodeList nodeListTerminal = document.getElementsByTagName("terminal");
            Node nodeTerminal = nodeListTerminal.item(0);
            NamedNodeMap terminalAttrs = nodeTerminal.getAttributes();

            //set  terminal id
            String terminalId = terminalAttrs.getNamedItem("id").getTextContent();
            terminal.setTerminalId(terminalId);

            //set terminal type
            String terminalType = terminalAttrs.getNamedItem("type").getTextContent();
            terminal.setTerminalType(terminalType);


            //..............................server...........................
            NodeList nodeListServer = document.getElementsByTagName("server");
            Node nodeServer = nodeListServer.item(0);
            NamedNodeMap serverAttrs = nodeServer.getAttributes();

            //set server IP
            String serverIP = serverAttrs.getNamedItem("ip").getTextContent();
            terminal.setServerIpAddress(serverIP);

            //set server port
            int port = Integer.parseInt(serverAttrs.getNamedItem("port").getTextContent());
            terminal.setPortNumber(port);

            //..............................outLog...........................
            NodeList nodeListOutLog = document.getElementsByTagName("outLog");
            Node nodeOutLog = nodeListOutLog.item(0);
            NamedNodeMap outLogAttrs = nodeOutLog.getAttributes();

            //set outLog path
            String outLogPath = outLogAttrs.getNamedItem("path").getTextContent();
            terminal.setOutLogPath(outLogPath);

            //............................transaction................................
            ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
            NodeList nodeListTransaction = document.getElementsByTagName("transaction");
            for (int counter = 0; counter < nodeListTransaction.getLength(); counter++) {
                Transaction transaction = new Transaction();
                transaction.setTerminalId(terminalId);
                Node nodeTransaction = nodeListTransaction.item(counter);
                NamedNodeMap transactionAttrs = nodeTransaction.getAttributes();

                //set transaction Id
                String transactionId = transactionAttrs.getNamedItem("id").getTextContent();
                transaction.setTransactionId(transactionId);

                //set transaction type
                String transactionType = transactionAttrs.getNamedItem("type").getTextContent();
                transaction.setTransactionType(transactionType);

                //set transaction amount
                BigDecimal transactionAmount = new BigDecimal((transactionAttrs.getNamedItem("amount").getTextContent().replaceAll(",", "")));
                transaction.setTransactionAmount(transactionAmount);

                //set transaction deposit Id
                String depositId = transactionAttrs.getNamedItem("deposit").getTextContent();
                transaction.setDepositId(depositId);

                //add to transaction list
                transactionList.add(transaction);
            }

            //set terminal transaction list.
            terminal.setTransactions(transactionList);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return terminal;
    }

    //Save response to XML file.............................
    public static void writeXml(Response serverResponse){
        String outputFilePath = "resources\\Response"+serverResponse.getTerminalId()+".xml";

        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document;
            File outputFile = new File(outputFilePath);
            Element rootElement;

            boolean fileExist = outputFile.exists();
            if (!fileExist) {
                document = documentBuilder.newDocument();
                rootElement = document.createElement("Responses");
                document.appendChild(rootElement);
            } else {

                document = documentBuilder.parse(outputFilePath);
                rootElement = document.getDocumentElement();
            }



            //Node root = document.getFirstChild();
            //rootElement = document.createElement("Responses");
            //document.appendChild(rootElement);

            Element responseElement = document.createElement("Response");

            responseElement.setAttribute("id" , serverResponse.getResponseId());
            responseElement.setAttribute("newBalance" , serverResponse.getNewBalance());
            responseElement.setAttribute("Message" , serverResponse.getResponseMessage());
            responseElement.setAttribute("TransactionType" , serverResponse.getTransactionType());
            responseElement.setAttribute("TerminalId" , serverResponse.getTerminalId());
            responseElement.setAttribute("CustomerId" , serverResponse.getCustomerId());

            rootElement.appendChild(responseElement);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(outputFile);
            transformer.setOutputProperty(OutputKeys.INDENT ,"yes" );
            transformer.transform(source, result);


        }catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}


