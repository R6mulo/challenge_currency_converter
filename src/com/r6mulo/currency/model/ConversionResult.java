package com.r6mulo.currency.model;

public class ConversionResult {
    private final double result;
    private final double rate;
    private final String date;

    public ConversionResult(double result, double rate, String date) {
        this.result = result;
        this.rate = rate;
        this.date = date;
    }

    public double getResult() { return result; }
    public double getRate() { return rate; }
    public String getDate() { return date; }
}
