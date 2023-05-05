package com.assignment.recommendationsystem.util;

import com.assignment.recommendationsystem.model.Crypto;

import java.util.Comparator;
import java.util.List;

public class CryptoUtils {

    public static final String NO_CRYPTO_FOUND = "No Crypto object found in the list";

    public static Crypto getOldestCrypto(List<Crypto> cryptos) {

        return cryptos.stream().min(Comparator.comparing(Crypto::getTimestamp)).orElseThrow(() -> new IllegalStateException(NO_CRYPTO_FOUND));
    }

    public static Crypto getNewestCrypto(List<Crypto> cryptos) {

        return cryptos.stream().max(Comparator.comparing(Crypto::getTimestamp)).orElseThrow(() -> new IllegalStateException(NO_CRYPTO_FOUND));
    }

    public static Crypto getMaxCrypto(List<Crypto> cryptos) {

        return cryptos.stream().max(Comparator.comparing(Crypto::getPrice)).orElseThrow(() -> new IllegalStateException(NO_CRYPTO_FOUND));
    }

    public static Crypto getMinCrypto(List<Crypto> cryptos) {

        return cryptos.stream().min(Comparator.comparing(Crypto::getPrice)).orElseThrow(() -> new IllegalStateException(NO_CRYPTO_FOUND));
    }
}
