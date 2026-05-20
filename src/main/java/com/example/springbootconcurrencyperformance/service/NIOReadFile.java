package com.example.springbootconcurrencyperformance.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import com.example.springbootconcurrencyperformance.model.Transaction;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * ## Canal de Leitura (FileChannel)
 * Um canal é como um "tubo" ou "pipeline" que conecta sua aplicação Java a uma fonte de dados (arquivo, rede, etc.).
 * Analogia prática: Imagine um canal como uma mangueira de jardim:
 * - A mangueira conecta a torneira (arquivo) ao seu balde (aplicação)
 * - A água (dados) flui através da mangueira
 * - Você controla quando abrir/fechar a "torneira" e quanto água pega

 * ## Buffer
 * Um buffer é como um "balde" ou "container temporário" que armazena dados na memória.
 * Analogia prática: Pense no buffer como um balde com marcações:
 * - Capacity (capacidade): Tamanho total do balde (1MB no seu código)
 * - Position (posição): Onde está o "nível atual" da água
 * - Limit (limite): Até onde você pode encher/esvaziar
 */

@Component
public final class NIOReadFile implements ReadFile {

    private static final int BUFFER_SIZE = 1024 * 1024; // 1MB

    @Override
    public List<Transaction> read() throws IOException {
        Path path = new ClassPathResource("transactions.csv").getFile().toPath();

        List<Transaction> transactions = new ArrayList<>();
        try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ)) {
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            StringBuilder lineBuilder = new StringBuilder();
            boolean headerSkipped = false; // Flag para controlar se o cabeçalho foi pulado

            while (channel.read(buffer) != -1 && transactions.size() < MAX_LINES) {
                buffer.flip(); // Prepara o buffer para leitura

                // Converte os bytes para string
                String content = StandardCharsets.UTF_8.decode(buffer).toString();
                lineBuilder.append(content);

                // Processa as linhas completas
                String[] lines = lineBuilder.toString().split("\n");

                int startIndex = 0;
                // Se ainda não pulou o cabeçalho, pula a primeira linha
                if (!headerSkipped && lines.length > 0) {
                    headerSkipped = true;
                    startIndex = 1;
                }


                // Processa todas as linhas exceto a última (que pode estar incompleta)
                for (int i = startIndex; i < lines.length - 1; i++) {
                    String line = lines[i].trim();
                    if (!line.isBlank()) {
                        String[] fields = line.split(",");
                        Transaction transaction = ReadFile.buildTransaction(fields);
                        transactions.add(transaction);

                        if (transactions.size() >= MAX_LINES) {
                            break;
                        }
                    }
                }

                // Mantém a última linha (possivelmente incompleta) para a próxima iteração
                lineBuilder = new StringBuilder();
                if (lines.length > 0) {
                    lineBuilder.append(lines[lines.length - 1]);
                }

                buffer.clear(); // Prepara o buffer para a próxima leitura
            }

            // Processa a última linha restante, se houver
            if (lineBuilder.length() > 0 && transactions.size() < MAX_LINES) {
                String line = lineBuilder.toString().trim();
                if (!line.isBlank()) {
                    String[] fields = line.split(",");
                    Transaction transaction = ReadFile.buildTransaction(fields);
                    transactions.add(transaction);
                }
            }
        }

        return transactions;

    }
}