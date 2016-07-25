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
public class XmlParser
{
    public static Terminal parseXml(String path) {
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
            //is this necessary???
            if (nodeTerminal.getNodeType() == Node.ATTRIBUTE_NODE) {
                NamedNodeMap terminalAttrs = nodeTerminal.getAttributes();
                //set  terminal id
                String terminalId = terminalAttrs.getNamedItem("id").getTextContent();
                terminal.setTerminalId(terminalId);
                //set terminal port
                String terminalType = terminalAttrs.getNamedItem("type").getTextContent();
                terminal.setTerminalType(terminalType);
        }

            //..............................server...........................
            NodeList nodeListServer = document.getElementsByTagName("server");
            Node nodeServer = nodeListServer.item(0);
            NamedNodeMap serverAttrs = nodeServer.getAttributes();
            //set server IP--------------------------------
            String serverIP = serverAttrs.getNamedItem("ip").getTextContent();
            terminal.setServerIpAddress(serverIP);
            //set server port------------------------------
            int port = Integer.parseInt(serverAttrs.getNamedItem("port").getTextContent());
            terminal.setPortNumber(port);

            //..............................outLog...........................
            NodeList nodeListOutLog = document.getElementsByTagName("outLog");
            Node nodeOutLog = nodeListOutLog.item(0);
            NamedNodeMap outLogAttrs = nodeOutLog.getAttributes();
            //set outLog path------------------------------
            String outLogPath = outLogAttrs.getNamedItem("path").getTextContent();
            terminal.setOutLogPath(outLogPath);

            //............................transaction................................
            ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
            NodeList nodeListTransaction = document.getElementsByTagName("transaction");
            for (int counter = 0; counter < nodeListTransaction.getLength(); counter++) {
                Transaction transaction = new Transaction();
                Node nodeTransaction = nodeListTransaction.item(counter);
                NamedNodeMap transactionAttrs = nodeTransaction.getAttributes();
                //set transaction Id-------------------------
                String transactionId = transactionAttrs.getNamedItem("id").getTextContent();
                transaction.setTransactionId(transactionId);
                //set transaction type------------------------
                String transactionType = transactionAttrs.getNamedItem("type").getTextContent();
                transaction.setTransactionType(transactionType);
                //set transaction amount----------------------
                BigDecimal transactionAmount = new BigDecimal((transactionAttrs.getNamedItem("amount").getTextContent().replaceAll(",", "")));
                transaction.setTransactionAmount(transactionAmount);
                //set transaction deposit Id------------------
                String depositId = transactionAttrs.getNamedItem("deposit").getTextContent();
                transaction.setDepositId(depositId);

                //add to transaction list
                transactionList.add(transaction);
                //set terminal transaction list.
            }
            terminal.setTransactions(transactionList);

        }catch(ParserConfigurationException e){
            e.printStackTrace();
        }catch(SAXException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return terminal;
    }

}


