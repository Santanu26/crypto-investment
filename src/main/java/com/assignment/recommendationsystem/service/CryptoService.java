package com.assignment.recommendationsystem.service;

import com.assignment.recommendationsystem.model.CryptoStats;
import com.assignment.recommendationsystem.model.HighestNormalizedCrypto;

import java.util.List;
import java.util.Map;

public interface CryptoService {
    CryptoStats getCryptoStats(String cryptoName);

    Map<String, CryptoStats> getAllCryptoStats();

    List<String> getSortedCryptosByNormalizedRange();

    HighestNormalizedCrypto getHighestNormalizedRange(long timestamp);
}

