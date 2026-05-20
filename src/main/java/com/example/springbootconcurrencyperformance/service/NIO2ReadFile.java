package com.example.springbootconcurrencyperformance.service;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.example.springbootconcurrencyperformance.model.Transaction;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public final class NIO2ReadFile implements ReadFile {

    @Override
    public List<Transaction> read() throws IOException {
        Path path = new ClassPathResource("transactions.csv").getFile().toPath();

        List<Transaction> transactions = new ArrayList<>();
//    List<String> lines = Files.readAllLines(path);
//
//    lines.stream().skip(1).filter(line -> !line.isBlank()).forEach(line -> {
//      String[] fields = line.split(",");
//      Transaction transaction = ReadFile.buildTransaction(fields);
//      transactions.add(transaction);
//    });


        try (final Stream<String> lines = Files.lines(path)) {
            lines
                    .skip(1)
                    .limit(MAX_LINES)
                    .filter(line -> !line.isBlank())
                    .forEach(line -> {
                        String[] fields = line.split(",");
                        Transaction transaction = ReadFile.buildTransaction(fields);
                        transactions.add(transaction);
                    });
        }

        return transactions;

    }
}
