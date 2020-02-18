package com.company;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Thread thread = Thread.currentThread();

        System.out.println("Current thread: " + thread.getName());

        //And now we want to see the name for one thread
        thread.setName("MyThread");

        System.out.println("Current thread: " + thread.getName());
        Thread.sleep(1000); //The program now does not end for 10 seconds
    }
}
