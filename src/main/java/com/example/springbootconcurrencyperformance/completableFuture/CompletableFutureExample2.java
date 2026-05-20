package com.example.springbootconcurrencyperformance.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.stereotype.Service;

@Service
public class CompletableFutureExample2 {
    static CompletableFuture<String> getOrderHistory() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {}
            return "Histórico de Pedidos";
        });
    }

    static CompletableFuture<String> getShippingPreferences() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1200);
            } catch (Exception e) {}
            return "Preferências de Envio";
        });
    }

    public void execute() {
        System.out.println("Iniciando busca de dados do cliente...");

        CompletableFuture<String> customerDataFuture =
                getOrderHistory().thenCombine(getShippingPreferences(), (history, prefs) -> {
                    return "Dados do Cliente:\n- " + history + "\n- " + prefs;
                });

        String customerData = customerDataFuture.join();
        System.out.println(customerData);
    }

}
