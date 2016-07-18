package com.server;

import com.client.Transaction;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Dotin school 6 on 7/18/2016.
 */
public class Validation {

    public void checkRequest(Transaction request) {
        JsonParserClass jsonParserClass = new JsonParserClass();
        System.out.println(jsonParserClass.depositList.size());
        for (int counter = 0; counter <jsonParserClass.depositList.size(); counter++) {
            System.out.println("Validation is running.....");
            Deposit deposit = jsonParserClass.depositList.get(counter);
            //System.out.println(request.getDepositId());
            //System.out.println(deposit.getCustomerId());
            if (deposit.getCustomerId().equals(request.getDepositId())) {
                System.out.println("I find customer....");
                try
                {
                    //reflection method invocation
                    Object validation = Validation.class.newInstance();
                    Method method =validation.getClass().getDeclaredMethod(request.getTransactionType(),Transaction.class,Deposit.class);
                    method.invoke(validation ,request ,deposit);

                } catch (NoSuchMethodException e)
                {
                    e.printStackTrace();
                } catch (InvocationTargetException e)
                {
                    e.printStackTrace();
                } catch (InstantiationException e)
                {
                    e.printStackTrace();
                } catch (IllegalAccessException e)
                {
                    e.printStackTrace();
                }
            } else
                System.out.println("Invalid request!");
        }

    }

    public void withdraw(Transaction transaction, Deposit deposit) {
        //System.out.println("I am withdraw");
        if (deposit.getInitialBalance().compareTo(transaction.getTransactionAmount())== -1){
            System.out.println("Withdraw operation is not possible.");
        }


    }

    public void deposit(Object transaction, Object deposit) {
        System.out.println("I am deposit");
    }

    public void setMessage() {
        System.out.println("I am set message.........");
    }
}
