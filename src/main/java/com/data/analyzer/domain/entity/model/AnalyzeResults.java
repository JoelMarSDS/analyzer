package com.data.analyzer.domain.entity.model;

public class AnalyzeResults {

    private Long amountClients;
    private Long amountSalesman;
    private Long bestSale;
    private String worstSeller;

    public AnalyzeResults(Long amountClients, Long amountSalesman, Long bestSale, String worstSeller) {
        this.amountClients = amountClients;
        this.amountSalesman = amountSalesman;
        this.bestSale = bestSale;
        this.worstSeller = worstSeller;
    }

    public AnalyzeResults() {
    }

    public Long getAmountClients() {
        return amountClients;
    }

    public Long getAmountSalesman() {
        return amountSalesman;
    }

    public Long getBestSale() {
        return bestSale;
    }

    public String getWorstSeller() {
        return worstSeller;
    }

    public static AnalyzeResults builder() {
        return new AnalyzeResults();
    }

    public AnalyzeResults amountClients(Long amountClients) {
        this.amountClients = amountClients;
        return this;
    }

    public AnalyzeResults amountSalesman(Long amountSalesman) {
        this.amountSalesman = amountSalesman;
        return this;
    }

    public AnalyzeResults bestSale(Long bestSale) {
        this.bestSale = bestSale;
        return this;
    }

    public AnalyzeResults worstSeller(String worstSeller) {
        this.worstSeller = worstSeller;
        return this;
    }

    public AnalyzeResults build() {
        return new AnalyzeResults(amountClients, amountSalesman, bestSale, worstSeller);
    }

    @Override
    public String toString() {
        return "AnalyzeResults{" +
                "amountClients=" + amountClients +
                ", amountSalesman=" + amountSalesman +
                ", bestSale=" + bestSale +
                ", worstSeller='" + worstSeller + '\'' +
                '}';
    }
}
