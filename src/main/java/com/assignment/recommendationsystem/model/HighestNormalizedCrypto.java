package com.assignment.recommendationsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HighestNormalizedCrypto {
    private long timestamp;
    private String symbol;
    private Double normalizedRange;
}