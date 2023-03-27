package com.example.root.intouchsmsapp.Models;

public class TransactionIds {

    private String token, transactionId;

    public TransactionIds() {
    }

    public TransactionIds(String token, String transactionId) {
        this.token = token;
        this.transactionId = transactionId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
