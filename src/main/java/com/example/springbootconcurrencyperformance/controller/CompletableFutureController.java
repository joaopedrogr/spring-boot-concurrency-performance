package com.example.springbootconcurrencyperformance.controller;

import com.example.springbootconcurrencyperformance.completableFuture.CompletableFutureExample1;
import com.example.springbootconcurrencyperformance.completableFuture.CompletableFutureExample2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController( "/completable-future")
public class CompletableFutureController {

    private final CompletableFutureExample1 completableFutureExample1;
    private final CompletableFutureExample2 completableFutureExample2;


    public CompletableFutureController(CompletableFutureExample1 completableFutureExample1,
                                       CompletableFutureExample2 completableFutureExample2) {
        this.completableFutureExample1 = completableFutureExample1;
        this.completableFutureExample2 = completableFutureExample2;
    }

    @PostMapping("/example1")
    public void executeExample1() {
        completableFutureExample1.execute();
    }

    @PostMapping("/example2")
    public void executeExample2() {
        completableFutureExample2.execute();
    }
}
