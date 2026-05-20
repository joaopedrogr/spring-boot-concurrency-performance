package com.example.springbootconcurrencyperformance.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.stereotype.Service;

@Service
public class CompletableFutureExample1 {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(2);

    record User(int id, String name) {}
    record Profile(int userId, String details) {}

    static CompletableFuture<User> getUserById(int id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("Buscando usuário " + id + "...");
                return new User(id, "Usuário " + id);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, executorService);
    }

    static CompletableFuture<Profile> getProfileForUser(User user) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Buscando perfil para " + user.name() + "...");
            return new Profile(user.id(), "Detalhes do perfil...");
        }, executorService);
    }

    public void execute() {
        CompletableFuture<Profile> flatFuture =
                getUserById(102).thenCompose(user -> getProfileForUser(user));
        System.out.println("Tipo de retorno com thenCompose: " + flatFuture.getClass().getSimpleName());

        Profile profile = flatFuture.join(); // Bloqueia para obter o resultado final
        System.out.println("Perfil obtido: " + profile);

    }
}
