package br.com.r6mulo.currencyConverter.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import br.com.r6mulo.currencyConverter.api.ApiClient;
import br.com.r6mulo.currencyConverter.model.ConversionResult;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class CurrencyService {

    private final ApiClient apiClient;
    private final Gson gson = new Gson();

    private final Map<String, String> symbolsCache = new ConcurrentHashMap<>();
    private final Map<String, Double> rateCache = new ConcurrentHashMap<>();

    public CurrencyService(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    // Carrega as moedas usando /codes
    public void loadSymbols() throws IOException, InterruptedException {
        String json = apiClient.getSymbols();
        JsonObject root = gson.fromJson(json, JsonObject.class);

        JsonArray arr = root.getAsJsonArray("supported_codes");
        if (arr == null) return;

        for (int i = 0; i < arr.size(); i++) {
            JsonArray item = arr.get(i).getAsJsonArray();
            String code = item.get(0).getAsString();
            String desc = item.get(1).getAsString();
            symbolsCache.put(code, desc);
        }
    }

    public Map<String, String> getSymbols() {
        return Collections.unmodifiableMap(new TreeMap<>(symbolsCache));
    }

    public boolean isValidCode(String code) {
        return symbolsCache.containsKey(code);
    }

    // Conversão usando /pair/{from}/{to}/{amount}
    public ConversionResult convert(String from, String to, double amount)
            throws IOException, InterruptedException {

        String json = apiClient.getConvert(from, to, amount);
        JsonObject root = gson.fromJson(json, JsonObject.class);

        String resultStatus = root.get("result").getAsString();
        if (!"success".equals(resultStatus)) {
            throw new IOException("API returned: " + resultStatus);
        }

        double result = root.get("conversion_result").getAsDouble();
        double rate = root.get("conversion_rate").getAsDouble();
        String date = root.get("time_last_update_utc").getAsString();

        return new ConversionResult(result, rate, date);
    }
}
