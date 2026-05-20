package com.example.springbootconcurrencyperformance.service;

import com.example.springbootconcurrencyperformance.model.Transaction;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public sealed interface ReadFile permits IOReadFile, NIO2ReadFile, NIOReadFile {
    int MAX_LINES = 1_000_000;

    List<Transaction> read() throws IOException;

    static Transaction buildTransaction(String[] line) {
        return new Transaction(
                Integer.parseInt(line[0]),
                line[1],
                new BigDecimal(line[2]),
                line[3],
                new BigDecimal(line[4]),
                new BigDecimal(line[5]),
                line[6],
                new BigDecimal(line[7]),
                new BigDecimal(line[8]),
                line[9].equals("1"),
                line[10].equals("1")
        );
    }
}
