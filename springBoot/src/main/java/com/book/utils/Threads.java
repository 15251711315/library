package com.book.utils;

public class Threads implements Runnable {
    String name = "";

    public Threads(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println(name + "         " + i);
        }
    }
}
