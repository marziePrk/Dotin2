package com.client;


import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Dotin school 6 on 7/12/2016.
 */
public class XmlReader
{
    Terminal terminal = new Terminal();
    Transaction transaction = new Transaction();
    ArrayList<Transaction> transactionList = new ArrayList<Transaction>();

    public  void readXml(String path) {
        try {
            File xmlFile = new File(path);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            //document.getDocumentElement().normalize();

            //.........................terminal...................................
            NodeList nodeListTerminal = document.getElementsByTagName("terminal");
            Node nodeTerminal = nodeListTerminal.item(0);
            System.out.println(nodeTerminal.getNodeName());
            System.out.println("........................................");
            //is this necessary???
            if (nodeTerminal.getNodeType() == Node.ATTRIBUTE_NODE) {
                NamedNodeMap terminalAttrs = nodeTerminal.getAttributes();
                //set  terminal id
                int terminalId = Integer.parseInt(terminalAttrs.getNamedItem("id").getTextContent());
                terminal.setTerminalId(terminalId);
                System.out.println("Terminal Id= " + terminalId);
                //set terminal port
                String terminalType = terminalAttrs.getNamedItem("type").getTextContent();
                terminal.setTerminalType(terminalType);
                System.out.println("Terminal Type= " + terminalType);
                System.out.println("\n");
            }

            //..............................server...........................
            NodeList nodeListServer = document.getElementsByTagName("server");
            Node nodeServer = nodeListServer.item(0);
            System.out.println(nodeServer.getNodeName());
            System.out.println("........................................");
                NamedNodeMap serverAttrs = nodeServer.getAttributes();
                //set server port
                int port = Integer.parseInt(serverAttrs.getNamedItem("port").getTextContent());
                System.out.println("port = " + port);
                terminal.setPortNumber(port);
                //set server IP
                String serverIP = serverAttrs.getNamedItem("ip").getTextContent();
                System.out.println("Server ip = " + serverIP);
                terminal.setServerIpAddress(serverIP);
                System.out.println("\n");
            //..............................outLog...........................
            NodeList nodeListOutLog = document.getElementsByTagName("outLog");
            Node nodeOutLog = nodeListOutLog.item(0);
            System.out.println(nodeOutLog.getNodeName());
            System.out.println("........................................");
                NamedNodeMap outLogAttrs = nodeOutLog.getAttributes();
                String outLogPath = outLogAttrs.getNamedItem("path").getTextContent();
                System.out.println("path = " + outLogPath);
                terminal.setOutLogPath(outLogPath);
                System.out.println("\n");
            //............................transaction................................
            NodeList nodeListTransaction = document.getElementsByTagName("transaction");
            for (int counter = 0; counter < nodeListTransaction.getLength(); counter++) {
                Node nodeTransaction = nodeListTransaction.item(counter);
                System.out.println(nodeTransaction.getNodeName());
                System.out.println("........................................");
                NamedNodeMap transactionAttrs = nodeTransaction.getAttributes();
                // set transaction Id
                int transactionId = Integer.parseInt(transactionAttrs.getNamedItem("id").getTextContent());
                System.out.println("id= " + transactionId);
                transaction.setTransactionId(transactionId);
                //set transaction type
                String transactionType = transactionAttrs.getNamedItem("type").getTextContent();
                System.out.println("type= " + transactionType);
                transaction.setTransactionType(transactionType);
                //set transaction amount
                BigDecimal transactionAmount = new BigDecimal((transactionAttrs.getNamedItem("amount").getTextContent().replaceAll(",", "")));
                System.out.println("amount= " + transactionAmount);
                transaction.setTransactionAmount(transactionAmount);
                //set transaction deposit Id
                int depositId = Integer.parseInt(transactionAttrs.getNamedItem("deposit").getTextContent());
                System.out.println("deposit= " + depositId);
                transaction.setDepositId(depositId);
                System.out.println("\n");
                transactionList.add(transaction);
            }
            System.out.println(transactionList);
                //System.out.println(terminal.getPortNumber());
            }catch(ParserConfigurationException e){
                e.printStackTrace();
            }catch(SAXException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }
        }

    }


