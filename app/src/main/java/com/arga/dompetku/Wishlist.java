package com.arga.dompetku;

public class Wishlist {
    private int id_wishlist;
    private int id_user;
    private String wish;
    private int tahun;
    private int harga;

    public Wishlist(int id_wishlist, int id_user, String wish, int tahun, int harga){
        this.setId_wishlist(id_wishlist);
        this.setId_user(id_user);
        this.setWish(wish);
        this.setTahun(tahun);
        this.setHarga(harga);
    }

    public Wishlist(String wish, int tahun, int harga){
        this.setWish(wish);
        this.setTahun(tahun);
        this.setHarga(harga);
    }


    public int getId_wishlist() {
        return id_wishlist;
    }

    public void setId_wishlist(int id_wishlist) {
        this.id_wishlist = id_wishlist;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getWish() {
        return wish;
    }

    public void setWish(String wish) {
        this.wish = wish;
    }

    public int getTahun() {
        return tahun;
    }

    public void setTahun(int tahun) {
        this.tahun = tahun;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }
}
