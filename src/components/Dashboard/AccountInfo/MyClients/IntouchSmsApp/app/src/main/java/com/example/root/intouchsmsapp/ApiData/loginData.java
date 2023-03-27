package com.example.root.intouchsmsapp.ApiData;

import com.google.gson.annotations.SerializedName;

public class loginData {


    private String username;
    private String password;

    public loginData(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
