package com.cheype.demo.quicksign;

import java.util.concurrent.ThreadPoolExecutor;

public class Main {

    public static void main(String args[]) throws InterruptedException {
        System.out.println("starting...");
        CitationProducer citationProducer = new CitationProducer();
        Thread t = new Thread(citationProducer);
        t.start();
        t.join();
    }
}
