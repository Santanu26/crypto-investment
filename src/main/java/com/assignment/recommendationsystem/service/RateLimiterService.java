package com.assignment.recommendationsystem.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterService {
    private static final Map<String, Bucket> buckets = new ConcurrentHashMap<>();
    @Value("${max.requests.per.second}")
    public int MAX_REQUESTS_PER_SECOND;

    public boolean allow(String ipAddress) {
        Bucket bucket = buckets.computeIfAbsent(ipAddress, k -> createNewBucket());
        return bucket.tryConsume(1);
    }

    private Bucket createNewBucket() {
        Refill refill = Refill.intervally(MAX_REQUESTS_PER_SECOND, Duration.ofSeconds(1));
        Bandwidth limit = Bandwidth.classic(MAX_REQUESTS_PER_SECOND, refill);
        return Bucket4j.builder().addLimit(limit).build();
    }
}
