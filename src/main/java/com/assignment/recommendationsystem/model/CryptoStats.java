package com.assignment.recommendationsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CryptoStats {
    private Crypto oldest;
    private Crypto newest;
    private Crypto min;
    private Crypto max;
}
