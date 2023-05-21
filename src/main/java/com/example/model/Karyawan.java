package com.example.model;

public class Karyawan {
    private String idKaryawan;
    private String namaKaryawan;
    private String nomorTelepon;

    public Karyawan(String idKaryawan, String namaKaryawan, String nomorTelepon) {
        this.idKaryawan = idKaryawan;
        this.namaKaryawan = namaKaryawan;
        this.nomorTelepon = nomorTelepon;
    }
    public String getIdKaryawan() {
        return idKaryawan;
    }
    public void setIdKaryawan(String idKaryawan) {
        this.idKaryawan = idKaryawan;
    }
    public String getNamaKaryawan() {
        return namaKaryawan;
    }
    public void setNamaKaryawan(String namaKaryawan) {
        this.namaKaryawan = namaKaryawan;
    }
    public String getNomorTelepon() {
        return nomorTelepon;
    }
    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }
}
