package com.example.root.intouchsmsapp.Models;

public class SentMessages {

    private String network, cost, from, to, /*external_message_id,*/ created_on, sent_on, message, status;
    private boolean expanded;

    public SentMessages(String network, String cost, String from, String to, /*String external_message_id,*/ String created_on, String sent_on, String message, String status) {
        this.network = network;
        this.cost = cost;
        this.from = from;
        this.to = to;
        /*this.external_message_id = external_message_id;*/
        this.created_on = created_on;
        this.sent_on = sent_on;
        this.message = message;
        this.status = status;
        this.expanded = false;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    /*public String getExternal_message_id() {
        return external_message_id;
    }

    public void setExternal_message_id(String external_message_id) {
        this.external_message_id = external_message_id;
    }*/

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getSent_on() {
        return sent_on;
    }

    public void setSent_on(String sent_on) {
        this.sent_on = sent_on;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }


}
