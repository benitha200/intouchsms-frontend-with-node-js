package com.example.root.intouchsmsapp.Models;

public class MyCustomersItems {

    private int id;
    private String customerno, username, names, lastlogin, datejoined, status, rateplan,
            customerbalance, phone, email, address, symbol, customerofficephone, customercommission,
            password,smsbalance;




    public MyCustomersItems() {
    }

    public MyCustomersItems(int id, String customerno, String username, String names, String lastlogin, String datejoined, String status, String rateplan, String customerbalance, String phone, String email, String address, String symbol, String customerofficephone, String customercommission, String password, String smsbalance) {
        this.id = id;
        this.customerno = customerno;
        this.username = username;
        this.names = names;
        this.lastlogin = lastlogin;
        this.datejoined = datejoined;
        this.status = status;
        this.rateplan = rateplan;
        this.customerbalance = customerbalance;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.symbol = symbol;
        this.customerofficephone = customerofficephone;
        this.customercommission = customercommission;
        this.password = password;
        this.smsbalance = smsbalance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerno() {
        return customerno;
    }

    public void setCustomerno(String customerno) {
        this.customerno = customerno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(String lastlogin) {
        this.lastlogin = lastlogin;
    }

    public String getDatejoined() {
        return datejoined;
    }

    public void setDatejoined(String datejoined) {
        this.datejoined = datejoined;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRateplan() {
        return rateplan;
    }

    public void setRateplan(String rateplan) {
        this.rateplan = rateplan;
    }

    public String getCustomerbalance() {
        return customerbalance;
    }

    public void setCustomerbalance(String customerbalance) {
        this.customerbalance = customerbalance;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCustomerofficephone() {
        return customerofficephone;
    }

    public void setCustomerofficephone(String customerofficephone) {
        this.customerofficephone = customerofficephone;
    }

    public String getCustomercommission() {
        return customercommission;
    }

    public void setCustomercommission(String customercommission) {
        this.customercommission = customercommission;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSmsbalance() {
        return smsbalance;
    }

    public void setSmsbalance(String smsbalance) {
        this.smsbalance = smsbalance;
    }
}
