package com.assignment.recommendationsystem.controller;

import com.assignment.recommendationsystem.model.CryptoStats;
import com.assignment.recommendationsystem.model.HighestNormalizedCrypto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CryptoControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private int port;
    @Test
    public void testGetDescendingSortedCryptosByNormalizedRange() throws Exception {
        List<String> expectedOrder = Arrays.asList("ETH", "XRP", "DOGE", "LTC", "BTC");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<List> response = restTemplate.exchange("http://localhost:" + port + "/sorted", HttpMethod.GET, entity, List.class);
        List<String> result = response.getBody();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(expectedOrder, result);
    }

    @Test
    public void testGetAllCryptoStats() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<Map> response = restTemplate.exchange("http://localhost:" + port + "/stats", HttpMethod.GET, entity, Map.class);
        Map<String, CryptoStats> result = response.getBody();
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
    @Test
    public void testGetCryptoStats() throws Exception {
        String cryptoName = "BTC";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<CryptoStats> response = restTemplate.exchange("http://localhost:" + port + "/" + cryptoName + "/stats", HttpMethod.GET, entity, CryptoStats.class);
        CryptoStats result = response.getBody();
        assertNotNull(result);
    }

    @Test
    public void testGetHighestNormalizedRange() throws Exception {
        long timestamp = 1641009600000L;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<HighestNormalizedCrypto> response = restTemplate.exchange("http://localhost:" + port + "/highest/" + timestamp, HttpMethod.GET, entity, HighestNormalizedCrypto.class);
        HighestNormalizedCrypto result = response.getBody();
        assertNotNull(result);
    }
}

