package com.example.springbootconcurrencyperformance.threads;

import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainScheduleThread {

    public static void main(String[] args) throws InterruptedException {
        try (ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4)) {
            scheduler.scheduleAtFixedRate(() -> {
                System.out.println("Tarefa periódica (fixed rate) executada às " + LocalTime.now());
                try {
                    Thread.sleep(1000); // Simula trabalho.
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, 0, 5, TimeUnit.SECONDS);

            Thread.currentThread().join();
        }
    }
}
