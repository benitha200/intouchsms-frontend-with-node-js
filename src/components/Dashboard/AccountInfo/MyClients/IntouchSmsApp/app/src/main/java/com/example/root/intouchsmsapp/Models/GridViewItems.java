package com.example.root.intouchsmsapp.Models;

import android.widget.ImageView;

public class GridViewItems {

    private int id;
    private String itemname;
    private Integer imageView;

    public GridViewItems(int id, String itemname, Integer imageView) {
        this.id = id;
        this.itemname = itemname;
        this.imageView = imageView;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public Integer getImageView() {
        return imageView;
    }

    public void setImageView(Integer imageView) {
        this.imageView = imageView;
    }
}
