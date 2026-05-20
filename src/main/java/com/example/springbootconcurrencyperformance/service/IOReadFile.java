package com.example.springbootconcurrencyperformance.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.example.springbootconcurrencyperformance.model.Transaction;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public final class IOReadFile implements ReadFile{

    @Override
    public List<Transaction> read() throws IOException {
        Path path = new ClassPathResource("transactions.csv").getFile().toPath();

        List<Transaction> transactions = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            reader.readLine(); // Pula header
            Transaction transaction = null;
            int count = 0;
            while(reader.readLine() != null && count++ < MAX_LINES){
                String line = reader.readLine();
                if(line.isBlank()) continue;
                String[] fields = line.split(",");
                transaction = ReadFile.buildTransaction(fields);
                transactions.add(transaction);
            }
        }

        return transactions;
    }
}