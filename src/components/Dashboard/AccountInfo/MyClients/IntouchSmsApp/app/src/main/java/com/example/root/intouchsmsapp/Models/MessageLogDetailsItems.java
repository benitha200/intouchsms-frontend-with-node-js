package com.example.root.intouchsmsapp.Models;

public class MessageLogDetailsItems {

    private int id;
    private String from, tocontact, to_phone, created, status, message;

    public MessageLogDetailsItems() {
    }

    public MessageLogDetailsItems(int id, String from, String tocontact, String to_phone, String created, String status, String message) {
        this.id = id;
        this.from = from;
        this.tocontact = tocontact;
        this.to_phone = to_phone;
        this.created = created;
        this.status = status;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTocontact() {
        return tocontact;
    }

    public void setTocontact(String tocontact) {
        this.tocontact = tocontact;
    }

    public String getTo_phone() {
        return to_phone;
    }

    public void setTo_phone(String to_phone) {
        this.to_phone = to_phone;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
