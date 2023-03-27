package com.example.root.intouchsmsapp.Models;

public class MessageLogSummaryItems {

    private int id;
    private String from, queued, delivered, undelivered, total, credits, message;

    public MessageLogSummaryItems() {
    }

    public MessageLogSummaryItems(int id, String from, String queued, String delivered, String undelivered, String total, String credits, String message) {
        this.id = id;
        this.from = from;
        this.queued = queued;
        this.delivered = delivered;
        this.undelivered = undelivered;
        this.total = total;
        this.credits = credits;
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

    public String getQueued() {
        return queued;
    }

    public void setQueued(String queued) {
        this.queued = queued;
    }

    public String getDelivered() {
        return delivered;
    }

    public void setDelivered(String delivered) {
        this.delivered = delivered;
    }

    public String getUndelivered() {
        return undelivered;
    }

    public void setUndelivered(String undelivered) {
        this.undelivered = undelivered;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
