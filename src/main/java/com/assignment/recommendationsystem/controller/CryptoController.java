package com.assignment.recommendationsystem.controller;

import com.assignment.recommendationsystem.exception.TooManyRequestsException;
import com.assignment.recommendationsystem.model.CryptoStats;
import com.assignment.recommendationsystem.model.HighestNormalizedCrypto;
import com.assignment.recommendationsystem.service.CryptoService;
import com.assignment.recommendationsystem.service.RateLimiterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CryptoController {

    @Value("${max.requests.per.second}")
    public int MAX_REQUESTS_PER_SECOND;
    private final CryptoService cryptoService;
    private final RateLimiterService rateLimiterService;

    @GetMapping("/sorted")
    public List<String> getDescendingSortedCryptosByNormalizedRange() {
        try {
            validateRateLimit();
        } catch (UnknownHostException e) {
            log.error("Unknown host exception.",e);
        }
        return cryptoService.getSortedCryptosByNormalizedRange();
    }

    @GetMapping("/stats")
    public Map<String, CryptoStats> getAllCryptoStats() {
        try {
            validateRateLimit();
        } catch (UnknownHostException e) {
            log.error("Unknown host exception.",e);
        }
        return cryptoService.getAllCryptoStats();
    }

    @GetMapping("/{cryptoName}/stats")
    public CryptoStats getCryptoStats(@PathVariable String cryptoName) {
        try {
            validateRateLimit();
        } catch (UnknownHostException e) {
            log.error("Unknown host exception.",e);
        }
        return cryptoService.getCryptoStats(cryptoName);
    }

    @GetMapping("/highest/{timestamp}")
    public HighestNormalizedCrypto getHighestNormalizedRange(@PathVariable long timestamp) {
        try {
            validateRateLimit();
        } catch (UnknownHostException e) {
            log.error("Unknown host exception.",e);
        }
        return cryptoService.getHighestNormalizedRange(timestamp);
    }

    public void validateRateLimit() throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        String ipAddress = localHost.getHostAddress();
        if (!rateLimiterService.allow(ipAddress)) {
            throw new TooManyRequestsException(ipAddress, MAX_REQUESTS_PER_SECOND);
        }
    }
}

