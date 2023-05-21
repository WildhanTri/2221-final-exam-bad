package com.example.model;

import java.sql.Date;

public class Customer {
    private String idCustomer;
    private String namaCustomer;
    private Date tanggalLahir;
    private String nomorTelepon;

    public Customer(String idCustomer, String namaCustomer, Date tanggalLahir, String nomorTelepon) {
        this.idCustomer = idCustomer;
        this.namaCustomer = namaCustomer;
        this.tanggalLahir = tanggalLahir;
        this.nomorTelepon = nomorTelepon;
    }

    public String getIdCustomer() {
        return idCustomer;
    }
    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }
    public String getNamaCustomer() {
        return namaCustomer;
    }
    public void setNamaCustomer(String namaCustomer) {
        this.namaCustomer = namaCustomer;
    }
    public Date getTanggalLahir() {
        return tanggalLahir;
    }
    public void setTanggalLahir(Date tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }
    public String getNomorTelepon() {
        return nomorTelepon;
    }
    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }
}
