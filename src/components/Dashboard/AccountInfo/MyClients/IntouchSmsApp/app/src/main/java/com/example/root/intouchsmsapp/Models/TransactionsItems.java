package com.example.root.intouchsmsapp.Models;

public class TransactionsItems {

    private int id;
    private String transaction, customerno, customername, smsquantity, openingbalance, amount, closingbalance, created, beneficiary;

    public TransactionsItems() {
    }

    public TransactionsItems(int id, String transaction, String customerno, String customername, String smsquantity, String openingbalance, String amount, String closingbalance, String created, String beneficiary) {
        this.id = id;
        this.transaction = transaction;
        this.customerno = customerno;
        this.customername = customername;
        this.smsquantity = smsquantity;
        this.openingbalance = openingbalance;
        this.amount = amount;
        this.closingbalance = closingbalance;
        this.created = created;
        this.beneficiary = beneficiary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getCustomerno() {
        return customerno;
    }

    public void setCustomerno(String customerno) {
        this.customerno = customerno;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getSmsquantity() {
        return smsquantity;
    }

    public void setSmsquantity(String smsquantity) {
        this.smsquantity = smsquantity;
    }

    public String getOpeningbalance() {
        return openingbalance;
    }

    public void setOpeningbalance(String openingbalance) {
        this.openingbalance = openingbalance;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getClosingbalance() {
        return closingbalance;
    }

    public void setClosingbalance(String closingbalance) {
        this.closingbalance = closingbalance;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
    }
}
