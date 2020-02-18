package com.company;

public class AndroidThreadTest implements Runnable {

    @Override
    public void run() {
        System.out.println("Starting Thread name: " + Thread.currentThread().getName());
        try {
            for (int i=0; i<10; i++) {
                System.out.println("I love Android");
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread completed: " + Thread.currentThread().getName());
    }
}