package com.arga.dompetku;

import java.util.Date;

public class User {
    private int id_user, wallet;
    private String username, fullname, city;

    public User(int id_user, String username, String fullname, String city, int wallet) {
        this.id_user = id_user;
        this.username = username;
        this.fullname = fullname;
        this.city = city;
        this.wallet = wallet;
    }

    public int getId() {
        return id_user;
    }

    public void setId(int id_user) {
        this.id_user = id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) { this.city = city; }

    public int getWallet(){ return wallet; }

    public void setWallet(int wallet){ this.wallet = wallet; }
}