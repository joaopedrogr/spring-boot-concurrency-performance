package com.example.springbootconcurrencyperformance.service;


import java.io.IOException;
import java.util.Map;

import com.example.springbootconcurrencyperformance.model.BenchmarkResultDTO;
import com.example.springbootconcurrencyperformance.model.Transaction;
import org.springframework.stereotype.Service;

@Service
public class HandleReadFile {

    private static final Map<Strategy, ReadFile> strategies = Map.of(
            Strategy.IO, new IOReadFile(),
            Strategy.NIO, new NIOReadFile(),
            Strategy.NIO2, new NIO2ReadFile()
    );

    static long usedMemory() {
        Runtime rt = Runtime.getRuntime();
        return rt.totalMemory() - rt.freeMemory();
    }

    public BenchmarkResultDTO execute(Strategy strategy) {
        try {
            long start = System.currentTimeMillis();


            var transactions = strategies.get(strategy).read();
            long lines = transactions.size();
            long fraudCount = transactions
                    .parallelStream()
                    .filter(Transaction::isFraud).count();
            double totalAmount = transactions
                    .parallelStream()
                    .mapToDouble(transaction -> transaction.amount().doubleValue())
                    .sum();

            long end = System.currentTimeMillis();
            long elapsedMs = end - start;

            return new BenchmarkResultDTO(
                    strategy.name(),
                    elapsedMs,
                    lines,
                    lines / (elapsedMs / 1000.0),
                    fraudCount,
                    totalAmount
            );
        } catch (IOException e) {
            throw new IllegalStateException("Erro ao ler arquivo");
        }


    }

    public enum Strategy {
        IO, NIO, NIO2, PLATFORM_THREADS, VIRTUAL_THREADS
    }
}
