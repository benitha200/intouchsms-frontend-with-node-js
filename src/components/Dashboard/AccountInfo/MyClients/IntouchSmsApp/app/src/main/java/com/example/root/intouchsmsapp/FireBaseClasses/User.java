package com.example.root.intouchsmsapp.FireBaseClasses;

public class User {

    public String date_of_birth;
    public String full_name;
    public String nickname;

    public User(String date_of_birth, String full_name, String nickname) {
        this.date_of_birth = date_of_birth;
        this.full_name = full_name;
        this.nickname = nickname;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
