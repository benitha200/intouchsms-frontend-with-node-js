package com.example.root.intouchsmsapp.Models;

public class CreditTransfersItems {

    private int id;
    private String transferno, customerno, customername, phone, email, amountransfered, created,
            desc, address;


    public CreditTransfersItems() {
    }

    public CreditTransfersItems(int id, String transferno, String customerno, String customername, String phone,
                                String email, String amountransfered, String created, String desc, String address) {
        this.id = id;
        this.transferno = transferno;
        this.customerno = customerno;
        this.customername = customername;
        this.phone = phone;
        this.email = email;
        this.amountransfered = amountransfered;
        this.created = created;
        this.desc = desc;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTransferno() {
        return transferno;
    }

    public void setTransferno(String transferno) {
        this.transferno = transferno;
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

    public String getAmountransfered() {
        return amountransfered;
    }

    public void setAmountransfered(String amountransfered) {
        this.amountransfered = amountransfered;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
