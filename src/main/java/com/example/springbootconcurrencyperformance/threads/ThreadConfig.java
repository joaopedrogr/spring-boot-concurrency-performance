package com.example.springbootconcurrencyperformance.threads;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadConfig {

    //    @Bean(name = "fixedThreadPool")
    public ExecutorService fixedThreadPool() {
        return Executors.newFixedThreadPool(4);
    }

    //    @Bean(name = "cachedThreadPool")
    public ExecutorService cachedThreadPool() {
        return Executors.newCachedThreadPool();
    }

    //    @Bean(name = "singleThreadPool")
    public ExecutorService singleThreadPool() {
        return Executors.newSingleThreadExecutor();
    }

    // Controle fino: corePool, maxPool, keepAlive, fila limitada e política de rejeição
    // @Bean(name = "customThreadPool")
    public ExecutorService customThreadPool() {
        return new ThreadPoolExecutor(
                2,                                  // corePoolSize: threads sempre ativas
                8,                                  // maximumPoolSize: limite de threads
                60L, TimeUnit.SECONDS,              // keepAliveTime: tempo ocioso antes de encerrar thread extra
                new ArrayBlockingQueue<>(100),      // workQueue: fila de tarefas pendentes (limite 100)
                new ThreadPoolExecutor.CallerRunsPolicy() // rejectionPolicy: quem enviou executa a tarefa
        );
    }
}
