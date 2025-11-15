package br.com.r6mulo.currencyConverter.api;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class ApiClient {

    // >>> INSIRA SUA KEY AQUI <<<
    private static final String API_KEY = "YOUR_API_KEY"; // ex: "7f6f349b833f9a36gg307159"

    private static final String BASE = "https://v6.exchangerate-api.com/v6/";

    private final HttpClient client = HttpClient.newBuilder().build();

    // Converte valores usando o formato da ExchangeRate-API:
    // /pair/{from}/{to}/{amount}
    public String getConvert(String from, String to, double amount) throws IOException, InterruptedException {
        String url = BASE + API_KEY + "/pair/" + from + "/" + to + "/" + amount;
        return get(url);
    }

    // Endpoint para listar moedas suportadas
    public String getSymbols() throws IOException, InterruptedException {
        String url = BASE + API_KEY + "/codes";
        return get(url);
    }

    private String get(String url) throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());

        if (resp.statusCode() != 200) {
            throw new IOException("HTTP error: " + resp.statusCode() + " -> " + resp.body());
        }

        return resp.body();
    }
}
