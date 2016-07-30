package com.client;

import java.io.IOException;

/**
 * Created by Dotin school 6 on 7/27/2016.
 */
public class TwoClientsRunning {
    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new ClientThread("resources\\terminal1.xml").start();
                    new ClientThread("resources\\terminal2.xml").start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
