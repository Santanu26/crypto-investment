package com.assignment.recommendationsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Crypto {
    private long timestamp;
    private String symbol;
    private Double price;
}