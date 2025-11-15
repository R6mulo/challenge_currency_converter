package br.com.r6mulo.currencyConverter.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ConversionHistory {

    private final List<String> history = new ArrayList<>();
    private int maxItems = 20; // Limite de Histórico (pode ajustar)

    private static final String JSON_FILE = "history.json";
    private static final String TXT_FILE = "history.json";

    // Métodos principais

    public void add(String entry) {
        history.add(entry);

        // controla tamanho máximo
        if (history.size() > maxItems) {
            history.remove(0); // remove o mais antigo
        }

        saveJson();  // salva automaticamente
        saveTxt();   // exporta automaticamente
    }

    public boolean isEmpty() {
        return history.isEmpty();
    }

    public void print() {
        if (history.isEmpty()) {
            System.out.println("Nenhum histórico disponível.\n");
            return;
        }

        System.out.println("\n===== Histórico de Converções =====");
        for (String h : history) {
            System.out.println(h);
        }
        System.out.println("===================================\n");
    }

    public  void clear() {
        history.clear();
        saveJson();
        saveTxt();
        System.out.println("Histórico apagado com sucesso!\n");
    }

    public void setMaxItems(int limit) {
        this.maxItems = limit;
    }

    // Salvar em Json

    private void saveJson() {
        try (FileWriter fw = new FileWriter(JSON_FILE)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(history, fw);
        } catch (IOException e) {
            System.out.println("Erro ao salvar JSON: " + e.getMessage());
        }
    }

    // Salvar em Txt

    private void saveTxt() {
        try {
            Files.write(Path.of(TXT_FILE), history);
        } catch (IOException e) {
            System.out.println("Erro ao salvar TXT: " + e.getMessage());
        }
    }
}
