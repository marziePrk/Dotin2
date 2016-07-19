package com.server;

import com.client.Transaction;

import java.math.BigDecimal;

/**
 * Created by Dotin school 6 on 7/18/2016.
 */
public class Validation {
    JsonParserClass jsonParserClass;

    public Validation() {
        jsonParserClass = new JsonParserClass();
    }

    public void findRequestId(Transaction request) {
        //check accuracy of transaction values
        if (checkTransactionValue(request)) {
            for (int counter = 0; counter < jsonParserClass.depositList.size(); counter++) {
                System.out.println("Validation is running.....");
                Deposit deposit = jsonParserClass.depositList.get(counter);
                if (deposit.getCustomerId().equals(request.getDepositId())) {
                    System.out.println("I find customer....");
                    //call deposit or withdraw
                    if (request.getTransactionType().equals("withdraw")) {
                        withdraw(request,deposit);
                        System.out.println("customer id ="+deposit.getCustomerId()+" new initial balance =" + deposit.getInitialBalance());

                    }else if (request.getTransactionType().equals("deposit")){
                        deposit(request,deposit);
                        System.out.println("customer id ="+deposit.getCustomerId()+" new initial balance =" + deposit.getInitialBalance());

                    }

                /* //reflection method invocation
                    Object validation = Validation.class.newInstance();
                    Method method =validation.getClass().getDeclaredMethod(request.getTransactionType(),Transaction.class,Deposit.class);
                    method.invoke(validation ,request ,deposit);
                    */
                } else
                    System.out.println("Invalid this customer id!!");
            }
        }
    }

    public void withdraw(Transaction transaction, Deposit deposit) {
        System.out.println("I am withdraw");
        if (deposit.getInitialBalance().compareTo(transaction.getTransactionAmount()) < 0) {
            System.out.println("Withdraw operation is not possible.");
        } else {
            BigDecimal newInitialBalance = deposit.getInitialBalance().subtract(transaction.getTransactionAmount());
            deposit.setInitialBalance(newInitialBalance);
        }
    }

    public void deposit(Transaction transaction, Deposit deposit) {
        System.out.println("I am deposit");
        BigDecimal newInitialBalance = deposit.getInitialBalance().add(transaction.getTransactionAmount());
        System.out.println(newInitialBalance);
        if(newInitialBalance.compareTo(deposit.getUpperBound()) > 0){
            deposit.setInitialBalance(newInitialBalance);
        }else System.out.println("Deposit operation in not possible!");
    }

    public boolean checkTransactionValue(Transaction transaction) {
       /* if (transaction.getTransactionId() > 0)
        {
            if (transaction.getTransactionType().equals("deposit") || transaction.getTransactionType().equals("withdraw"))
            {
                if (transaction.getTransactionAmount().compareTo(BigDecimal.ZERO)>0)
                {
                    if (transaction.getDepositId() > 0) {
                        return true;
                    }else
                        System.out.println("Negative deposit id!");
                }else
                    System.out.println("Negative Transaction amount!");

            }else
                System.out.println("Invalid transaction type!");
        }else
            System.out.println("Negative transaction id!");

        return false;*/
        return true;
    }
}
