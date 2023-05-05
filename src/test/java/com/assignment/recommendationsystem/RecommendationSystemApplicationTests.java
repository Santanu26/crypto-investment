package com.assignment.recommendationsystem;

import com.assignment.recommendationsystem.controller.CryptoController;
import com.assignment.recommendationsystem.model.CryptoStats;
import com.assignment.recommendationsystem.model.HighestNormalizedCrypto;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
class RecommendationSystemApplicationTests {

    @Autowired
    private CryptoController cryptoController;

    @Test
    public void testGetDescendingSortedCryptosByNormalizedRange() {
        List<String> expectedOrder = Arrays.asList("ETH", "XRP", "DOGE", "LTC", "BTC");
        List<String> result = cryptoController.getDescendingSortedCryptosByNormalizedRange();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isEmpty());
        Assert.assertEquals(expectedOrder, result);
    }

    @Test
    public void testGetAllCryptoStats() throws Exception {
        Map<String, CryptoStats> result = cryptoController.getAllCryptoStats();
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isEmpty());
    }

    @Test
    public void testGetCryptoStats() throws Exception {
        String cryptoName = "BTC";
        CryptoStats result = cryptoController.getCryptoStats(cryptoName);
        Assert.assertNotNull(result);
    }

    @Test
    public void testGetHighestNormalizedRange() throws Exception {
        long timestamp = 1641009600000L;
        HighestNormalizedCrypto result = cryptoController.getHighestNormalizedRange(timestamp);
        Assert.assertNotNull(result);
    }
}



