package com.client;

/**
 * Created by Dotin school 6 on 7/27/2016.
 */
public class TwoClientsMain {
    public static void main(String[] args) {
        //Thread threadTerminal1 = new ClientTread("resources\\terminal1.xml");
        //Thread threadTerminal2 = new ClientTread("resources\\terminal2.xml");

        //threadTerminal1.start();
        //threadTerminal2.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                new ClientTread("resources\\terminal1.xml").start();
                new ClientTread("resources\\terminal2.xml").start();
            }
        }).start();
    }
}
