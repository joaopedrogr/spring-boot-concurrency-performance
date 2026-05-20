package com.example.springbootconcurrencyperformance.controller;

import com.example.springbootconcurrencyperformance.model.BenchmarkResultDTO;
import com.example.springbootconcurrencyperformance.service.HandleReadFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController( "/transactions")
public class ReadFileController {

    private final HandleReadFile handleReadFile;

    public ReadFileController(HandleReadFile handleReadFile) {
        this.handleReadFile = handleReadFile;
    }

    @PostMapping("/io")
    public BenchmarkResultDTO readIo() {
        return this.handleReadFile.execute(HandleReadFile.Strategy.IO);
    }

    @PostMapping("/nio")
    public BenchmarkResultDTO readNio() {
        return this.handleReadFile.execute(HandleReadFile.Strategy.NIO);
    }

    @PostMapping("/nio2")
    public BenchmarkResultDTO readNio2() {
        return this.handleReadFile.execute(HandleReadFile.Strategy.NIO2);
    }
}
