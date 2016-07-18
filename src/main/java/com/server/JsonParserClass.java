package com.server;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Dotin school 6 on 7/17/2016.
 */
public class JsonParserClass
{
   private  static int port;
   public ArrayList<Deposit> depositList = new ArrayList<Deposit>();

    public static int getPort() {
        return port;
    }

    public void readJson(String path) {
        try {
            FileReader jsonFile = new FileReader(path);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonFileObject = (JSONObject) jsonParser.parse(jsonFile);
            port = ((Number)jsonFileObject.get("port")).intValue();
            System.out.println("port =" +port);
            String outLog =(String)jsonFileObject.get("outLog");
            System.out.println("outLog =" +outLog);
            JSONArray depositAray = (JSONArray) jsonFileObject.get("deposits");

            for (int counter = 0 ; counter < depositAray.size() ; counter++) {
                Deposit deposit = new Deposit();
                JSONObject depositObject = (JSONObject) depositAray.get(counter);
                //set customer
                String customer = (String) depositObject.get("customer");
                System.out.println("Customer = " + customer);
                deposit.setCustomer(customer);
                //set id
                String id = (String) depositObject.get("id");
                System.out.println("id =" + id);
                deposit.setId(id);
                //set initial Balance
                String initialBalanceString = (String) depositObject.get("initialBalance");
                BigDecimal initialBalance = new BigDecimal(initialBalanceString.replaceAll(",",""));
                System.out.println("initialBalance =" + initialBalance);
                deposit.setInitialBalance(initialBalance);
                //set upper Bound
                String upperBoundString = (String) depositObject.get("initialBalance");
                BigDecimal upperBound = new BigDecimal(upperBoundString.replaceAll(",",""));
                System.out.println("upperBound =" +upperBound);
                deposit.setUpperBound(upperBound);
                System.out.println("\n");
                depositList.add(deposit);
            }
            System.out.println(depositList);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
