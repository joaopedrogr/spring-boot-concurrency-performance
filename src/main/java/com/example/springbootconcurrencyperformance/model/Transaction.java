package com.example.springbootconcurrencyperformance.model;

import java.math.BigDecimal;

public record Transaction(
        int step,
        String type,
        BigDecimal amount,
        String nameOrig,
        BigDecimal oldBalanceOrig,
        BigDecimal newBalanceOrig,
        String nameDest,
        BigDecimal oldBalanceDest,
        BigDecimal newBalanceDest,
        boolean isFraud,
        boolean isFlaggedFraud) {
}
