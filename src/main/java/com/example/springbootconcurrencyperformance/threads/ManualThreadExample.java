package com.example.springbootconcurrencyperformance.threads;

import org.springframework.stereotype.Service;

@Service
public class ManualThreadExample {
    public static class ManualThread extends Thread {
        @Override
        public void run() {
            System.out.println("Iniciando thread manual em " + Thread.currentThread());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Finalizando thread manual em " + Thread.currentThread());
        }
    }

    public void execute() {
        ManualThread thread = new ManualThread();
        thread.start();
    }
}
