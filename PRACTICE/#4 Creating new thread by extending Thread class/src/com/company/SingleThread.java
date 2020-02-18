package com.company;

public class SingleThread {

    public static void main(String[] args) throws InterruptedException {
        //MAKING RUN 3 LOOPS AT THE SAME TIME
        createJavaThread();
        createJAVAFXThread();
        createAndroidThread();
    }

    private static void createJavaThread(){
        new JAVAThreadTest().start();
//        Thread thread = new Thread();
//        thread.start();

        //WHEN WE CALL START METHOD, RUN METHOD IS CALLED
    }

    private static void createJAVAFXThread(){
        new JAVAFXThreadTest().start();
    }

    private static void createAndroidThread(){
        AndroidThreadTest androidThreadTest = new AndroidThreadTest();

        Thread thread = new Thread(androidThreadTest);
        thread.setName("ANDROID_THREAD");

        thread.start();
    }
}