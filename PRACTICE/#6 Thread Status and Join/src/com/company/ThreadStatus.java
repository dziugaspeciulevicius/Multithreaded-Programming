package com.company;

public class ThreadStatus {

    public static void main(String[] args) throws InterruptedException {

        Thread th = new Thread();
        CalculatorRunnable runnable = new CalculatorRunnable(300000000000L);

        Thread thread = new Thread(runnable);
        thread.setName("High Priority");
        thread.start();

        //we can always check if thread is still running with .isAlive
//        while(thread.isAlive()){
//            System.out.println("Thread is active");
//            Thread.sleep(100);
//        }
//
//        System.out.println("Thread is completed.");


        //our main thread will wait for thread to complete with .join
//        thread.join();
//        System.exit(0);

        thread.join(5000); //if we add join, it will wait as long as needed, but if we add time, it will kill the thread when time has elapsed
        System.out.println("Join completed");
        System.exit(0);
    }
}
