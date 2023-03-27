package com.example.root.intouchsmsapp.Models;

public class PublishedPackagesItems {

    private int id;
    private String name, mincredits, maxcredits, unitprice, min_price, max_price;
    private Boolean maximum_null;
    public PublishedPackagesItems() {
    }

    public PublishedPackagesItems(int id, String name, String mincredits, String maxcredits, String unitprice, String min_price, String max_price, Boolean maximum_null) {
        this.id = id;
        this.name = name;
        this.mincredits = mincredits;
        this.maxcredits = maxcredits;
        this.unitprice = unitprice;
        this.min_price = min_price;
        this.max_price = max_price;
        this.maximum_null = maximum_null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMincredits() {
        return mincredits;
    }

    public void setMincredits(String mincredits) {
        this.mincredits = mincredits;
    }

    public String getMaxcredits() {
        return maxcredits;
    }

    public void setMaxcredits(String maxcredits) {
        this.maxcredits = maxcredits;
    }

    public String getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(String unitprice) {
        this.unitprice = unitprice;
    }

    public String getMin_price() {
        return min_price;
    }

    public void setMin_price(String min_price) {
        this.min_price = min_price;
    }

    public String getMax_price() {
        return max_price;
    }

    public void setMax_price(String max_price) {
        this.max_price = max_price;
    }

    public Boolean getMaximum_null() {
        return maximum_null;
    }

    public void setMaximum_null(Boolean maximum_null) {
        this.maximum_null = maximum_null;
    }
}
