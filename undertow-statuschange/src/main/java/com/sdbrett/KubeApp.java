package com.sdbrett;


import java.util.concurrent.atomic.AtomicBoolean;

public class KubeApp {
    static AtomicBoolean running = new AtomicBoolean(false);

    public static void main(String[] args) {


        System.out.println("Define server");
        Liveness server = new Liveness(8081);

        server.allGoodInTheHood = new AtomicBoolean(true);

        try{
            Thread.sleep(10000);
        } catch (Exception e){}

        server.allGoodInTheHood = new AtomicBoolean(false);
    }
}
