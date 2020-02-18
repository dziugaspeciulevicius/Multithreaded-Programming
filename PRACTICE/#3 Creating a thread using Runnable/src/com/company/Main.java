package com.company;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        //MAKING RUN 3 LOOPS AT THE SAME TIME
        createJavaThread();
        createJAVAFXThread();
        createAndroidThread();
    }



    private static void createJavaThread(){
        JAVAThreadTest threadTest = new JAVAThreadTest();

        Thread thread = new Thread(threadTest);
        thread.setName("JAVA_THREAD");

        thread.start();
    }

    private static void createJAVAFXThread(){
        JAVAFXThreadTest javafxThreadTest = new JAVAFXThreadTest();

        Thread thread = new Thread(javafxThreadTest);
        thread.setName("JAVAFX_THREAD");

        thread.start();
    }

    private static void createAndroidThread(){
        AndroidThreadTest androidThreadTest = new AndroidThreadTest();

        Thread thread = new Thread(androidThreadTest);
        thread.setName("ANDROID_THREAD");

        thread.start();
    }

}
