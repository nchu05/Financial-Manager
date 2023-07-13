package com.example.financialmanager;

public class PaymentModel {
    String transaction;
    String category;
    String cost;

    public PaymentModel (String transaction, String category, String cost) {
        this.transaction = transaction;
        this.category = category;
        this.cost = cost;
    }


    public String getTransaction() {
        return transaction;
    }

    public String getCategory() {
        return category;
    }

    public String getCost() {
        return cost;
    }
}
