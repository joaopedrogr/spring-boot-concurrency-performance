package com.example.springbootconcurrencyperformance.model;

public record BenchmarkResultDTO(String strategy,
                                 long executionTimeMs,
                                 long linesProcessed,
                                 double throughputLinesPerSecond,
                                 long fraudCount,
                                 double totalAmountProcessed) {

}