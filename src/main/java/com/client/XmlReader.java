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

import static com.client.Terminal.getTerminal;
import static com.client.Terminal.setTerminal;
import static com.client.Transaction.*;

/**
 * Created by Dotin school 6 on 7/12/2016.
 */
public class XmlReader
{
    public static void readXml(String path) {
        try {
            File xmlFile = new File(path);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            //document.getDocumentElement().normalize();

            //.........................terminal...................................
            setTerminal(new Terminal());
            NodeList nodeListTerminal = document.getElementsByTagName("terminal");
            Node nodeTerminal = nodeListTerminal.item(0);
            //is this necessary???
            if (nodeTerminal.getNodeType() == Node.ATTRIBUTE_NODE) {
                NamedNodeMap terminalAttrs = nodeTerminal.getAttributes();
                //set  terminal id
                String terminalId = terminalAttrs.getNamedItem("id").getTextContent();
                getTerminal().setTerminalId(terminalId);
                //set terminal port
                String terminalType = terminalAttrs.getNamedItem("type").getTextContent();
                getTerminal().setTerminalType(terminalType);
            }

            //..............................server...........................
            NodeList nodeListServer = document.getElementsByTagName("server");
            Node nodeServer = nodeListServer.item(0);
            NamedNodeMap serverAttrs = nodeServer.getAttributes();
            //set server IP--------------------------------
            String serverIP = serverAttrs.getNamedItem("ip").getTextContent();
            getTerminal().setServerIpAddress(serverIP);
            //set server port------------------------------
            int port = Integer.parseInt(serverAttrs.getNamedItem("port").getTextContent());
            getTerminal().setPortNumber(port);

            //..............................outLog...........................
            NodeList nodeListOutLog = document.getElementsByTagName("outLog");
            Node nodeOutLog = nodeListOutLog.item(0);
            NamedNodeMap outLogAttrs = nodeOutLog.getAttributes();
            //set outLog path------------------------------
            String outLogPath = outLogAttrs.getNamedItem("path").getTextContent();
            getTerminal().setOutLogPath(outLogPath);

            //............................transaction................................
            setTransactionList(new ArrayList<Transaction>());
            NodeList nodeListTransaction = document.getElementsByTagName("transaction");
            for (int counter = 0; counter < nodeListTransaction.getLength(); counter++) {
                setTransaction(new Transaction());
                Node nodeTransaction = nodeListTransaction.item(counter);
                NamedNodeMap transactionAttrs = nodeTransaction.getAttributes();
                //set transaction Id-------------------------
                String transactionId = transactionAttrs.getNamedItem("id").getTextContent();
                getTransaction().setTransactionId(transactionId);
                //set transaction type------------------------
                String transactionType = transactionAttrs.getNamedItem("type").getTextContent();
                getTransaction().setTransactionType(transactionType);
                //set transaction amount----------------------
                BigDecimal transactionAmount = new BigDecimal((transactionAttrs.getNamedItem("amount").getTextContent().replaceAll(",", "")));
                getTransaction().setTransactionAmount(transactionAmount);
                //set transaction deposit Id------------------
                String depositId = transactionAttrs.getNamedItem("deposit").getTextContent();
                getTransaction().setDepositId(depositId);

                //add to transaction list
                getTransactionList().add(getTransaction());
            }

        }catch(ParserConfigurationException e){
            e.printStackTrace();
        }catch(SAXException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}


