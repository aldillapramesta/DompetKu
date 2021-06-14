package com.arga.dompetku;

import java.util.Date;

public class History {
    private int id_history;
    private int id_user;
    private int nominal;
    private String jenis;
    private String kegiatan;
    private String tanggal;

    public History(int id_history, int id_user, String jenis, int nominal, String tanggal, String kegiatan){
        this.setId_history(id_history);
        this.setId_user(id_user);
        this.setJenis(jenis);
        this.setNominal(nominal);
        this.setTanggal(tanggal);
        this.setKegiatan(kegiatan);
    }

    public History(int nominal, String tanggal){
        this.setNominal(nominal);
        this.setTanggal(tanggal);
    }

    public History(String jenis, int nominal, String tanggal, String kegiatan){
        this.setJenis(jenis);
        this.setNominal(nominal);
        this.setTanggal(tanggal);
        this.setKegiatan(kegiatan);
    }

    public int getId_history() {
        return id_history;
    }

    public void setId_history(int id_history) {
        this.id_history = id_history;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getKegiatan() {
        return kegiatan;
    }

    public void setKegiatan(String kegiatan) {
        this.kegiatan = kegiatan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
