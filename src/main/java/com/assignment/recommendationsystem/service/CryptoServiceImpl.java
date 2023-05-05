package com.assignment.recommendationsystem.service;

import com.assignment.recommendationsystem.model.Crypto;
import com.assignment.recommendationsystem.model.CryptoStats;
import com.assignment.recommendationsystem.model.HighestNormalizedCrypto;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static com.assignment.recommendationsystem.util.CryptoUtils.*;

@Service
public class CryptoServiceImpl implements CryptoService {
    private static final String CSV_FILE_SUFFIX_LABEL = "_values.csv";
    private final String CSV_FOLDER = "/Users/Santanu_Barua/Documents/assignment/recommendation-system/prices";
    private final List<String> CRYPTO_NAMES = new ArrayList<>();
    private final Map<String, List<Crypto>> cryptosByCryptoName = new HashMap<>();

    @PostConstruct
    public void init() throws IOException {

        File[] csvFiles = new File(CSV_FOLDER).listFiles((dir, name) -> name.endsWith(CSV_FILE_SUFFIX_LABEL));

        for (File csv : Objects.requireNonNull(csvFiles)) {
            List<Crypto> cryptos = new ArrayList<>();
            String csvFileName = csv.getName().split("_")[0];

            try (BufferedReader br = new BufferedReader(new FileReader(csv))) {
                br.readLine(); // Skip the first row
                String csvRow;
                while ((csvRow = br.readLine()) != null) {
                    String[] cols = csvRow.split(",");
                    if (cols[0] != null && cols[1] != null && cols[2] != null) {
                        Crypto crypto = new Crypto(Long.parseLong(cols[0].trim()), cols[1].trim(), Double.parseDouble(cols[2].trim()));
                        cryptos.add(crypto);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            CRYPTO_NAMES.add(csvFileName);
            cryptosByCryptoName.put(csvFileName, cryptos);
        }
    }

    @Override
    public List<String> getSortedCryptosByNormalizedRange() {
        List<String> cryptoNames = new ArrayList<>(cryptosByCryptoName.keySet());

        cryptoNames.sort((o1, o2) -> {
            double range1 = getNormalizedRange(cryptosByCryptoName.get(o1));
            double range2 = getNormalizedRange(cryptosByCryptoName.get(o2));
            return Double.compare(range2, range1);
        });
        return cryptoNames;
    }

    @Override
    public CryptoStats getCryptoStats(String cryptoName) {
        List<Crypto> cryptos = cryptosByCryptoName.get(cryptoName);
        CryptoStats stats = new CryptoStats();

        stats.setOldest(getOldestCrypto(cryptos));
        stats.setNewest(getNewestCrypto(cryptos));
        stats.setMin(getMinCrypto(cryptos));
        stats.setMax(getMaxCrypto(cryptos));

        return stats;
    }

    private double getNormalizedRange(List<Crypto> cryptos) {

        double minPrice = getMinCrypto(cryptos).getPrice();
        double maxPrice = getMaxCrypto(cryptos).getPrice();
        return (maxPrice - minPrice) / minPrice;
    }

    @Override
    public HighestNormalizedCrypto getHighestNormalizedRange(long timestamp) {
        double highestRange = 0;

        HighestNormalizedCrypto highestNormalizedCrypto = new HighestNormalizedCrypto();
        for (Map.Entry<String, List<Crypto>> entry : cryptosByCryptoName.entrySet()) {
            List<Crypto> cryptos = cryptosByCryptoName.get(entry.getKey());
            double normalizedRange = getNormalizedRange(cryptos);
            if (highestRange < normalizedRange) {
                highestRange = normalizedRange;
                highestNormalizedCrypto.setNormalizedRange(highestRange);
                highestNormalizedCrypto.setTimestamp(timestamp);
                highestNormalizedCrypto.setSymbol(entry.getKey());
            }
        }

        return highestNormalizedCrypto;
    }

    @Override
    public Map<String, CryptoStats> getAllCryptoStats() {
        Map<String, CryptoStats> allCryptoStats = new HashMap<>();
        for (String cryptoName : CRYPTO_NAMES) {
            allCryptoStats.put(cryptoName, getCryptoStats(cryptoName));
        }
        return allCryptoStats;
    }
}
