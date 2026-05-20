package com.example.springbootconcurrencyperformance.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Service;

@Service
public class FixedThreadExample {

    public void execute() {
        // O número de threads é dimensionado de acordo com os núcleos do processador.
        int coreCount = Runtime.getRuntime().availableProcessors();
        System.out.println("Número de núcleos de CPU: " + coreCount);

        AtomicInteger counter = new AtomicInteger();
        ThreadFactory threadFactory = task -> {
            Thread thread = new Thread(task);
            thread.setName("fixed-thread-" + counter.getAndIncrement());
            return thread;
        };

        long start = System.currentTimeMillis();

        // try-with-resources garante que o executor seja desligado.
        try (ExecutorService executor = Executors.newFixedThreadPool(coreCount * 2 , threadFactory)) {
            // Submete N tarefas CPU-bound.
            for (int i = 0; i < coreCount * 2; i++) {
                final int taskNumber = i;
                executor.submit(() -> {
                    System.out.println("Iniciando tarefa CPU-bound " + taskNumber + " em " + Thread.currentThread().toString());
                    // Simula um trabalho computacionalmente intensivo.
                    long result = performIntensiveCalculation();
                    System.out.println("Tarefa " + taskNumber + " concluída com resultado " + result + " em " + Thread.currentThread().toString());
                });
            }
        } // executor.shutdown() é chamado automaticamente aqui.
        long end = System.currentTimeMillis();
        System.out.println("Acabou tudo em " + (end - start) + "ms");
    }

    private static long performIntensiveCalculation() {
        // Simulação de uma tarefa que mantém a CPU ocupada.
        long sum = 0;
        for (int i = 0; i < 1_000_000_000; i++) {
            sum += i;
        }
        return sum;
    }
}
