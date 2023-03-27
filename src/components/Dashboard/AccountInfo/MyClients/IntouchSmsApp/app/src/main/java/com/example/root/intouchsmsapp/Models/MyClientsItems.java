package com.example.root.intouchsmsapp.Models;

public class MyClientsItems {

    private int id;
    private String customerno, names, username, status, phone, email,officephone,
            password, address, accountbal,commission, desc, datejoined, lastlogin;

    public MyClientsItems() {
    }

    public MyClientsItems(int id, String customerno, String names, String username, String status, String phone, String email, String officephone, String password, String address, String accountbal, String commission, String desc, String datejoined, String lastlogin) {
        this.id = id;
        this.customerno = customerno;
        this.names = names;
        this.username = username;
        this.status = status;
        this.phone = phone;
        this.email = email;
        this.officephone = officephone;
        this.password = password;
        this.address = address;
        this.accountbal = accountbal;
        this.commission = commission;
        this.desc = desc;
        this.datejoined = datejoined;
        this.lastlogin = lastlogin;
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

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getOfficephone() {
        return officephone;
    }

    public void setOfficephone(String officephone) {
        this.officephone = officephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAccountbal() {
        return accountbal;
    }

    public void setAccountbal(String accountbal) {
        this.accountbal = accountbal;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDatejoined() {
        return datejoined;
    }

    public void setDatejoined(String datejoined) {
        this.datejoined = datejoined;
    }

    public String getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(String lastlogin) {
        this.lastlogin = lastlogin;
    }
}
