package com.server;

/**
 * Created by Dotin school 6 on 7/18/2016.
 */
public class Validation {
   /* JsonParser jsonParser = new JsonParser();
    List<Deposit> depositList = jsonParser.readDepositsInfo("resources\\core.json");

    public Deposit findAccount(Transaction transaction) {

        for (int counter = 0; counter < depositList.size(); counter++) {
            Deposit deposit = depositList.get(counter);
            if (deposit.getCustomerId().equals(transaction.getDepositId())) {
                return deposit;
            }

                *//* //reflection method invocation
                    Object validation = Validation.class.newInstance();
                    Method method =validation.getClass().getDeclaredMethod(transaction.getTransactionType(),Transaction.class,Deposit.class);
                    method.invoke(validation ,transaction ,deposit);
                    *//*
        }
        return  null;
    }*/

   /* public Response requestProcessing(Transaction request, Deposit deposit) {
        Response response = null;
        String success = "Operation was successful";
        String fail = "Operation was unsuccessful";
      if (request.getTransactionType().equals("deposit")) {
            BigDecimal initialBalance = deposit.getInitialBalance().add(request.getTransactionAmount());
            if (initialBalance.compareTo(deposit.getUpperBound()) < 0) {
                deposit.doDeposit(request, deposit);
                response = new Response(request.getTransactionId(), deposit.getInitialBalance(), success, request.getTransactionType());
            }else {
                response = new Response(request.getDepositId(), deposit.getInitialBalance(), fail, request.getTransactionType());
            }

        } else if (request.getTransactionType().equals("withdraw")) {
            if (deposit.getInitialBalance().compareTo(request.getTransactionAmount()) > 0) {
                deposit.doWithdraw(request, deposit);
                response = new Response(request.getTransactionId(), deposit.getInitialBalance(), success, request.getTransactionType());
            } else
                response = new Response(request.getTransactionId(), deposit.getInitialBalance(), fail, request.getTransactionType());
        }
        return response;
    }*/
}
  /*  public boolean checkTransactionValue(Transaction transaction) {
        if (transaction.getTransactionId() > 0)
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

        return false;
    }*/
