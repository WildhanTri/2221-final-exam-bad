package com.example.model;

public class Order {
    private String kodeBuku;
    private String namaBuku;
    private String tglPeminjaman;
    private String tglPengembalian;
    private String harga;

    public Order(String kodeBuku, String namaBuku, String tglPeminjaman, String tglPengembalian, String harga) {
        this.kodeBuku = kodeBuku;
        this.namaBuku = namaBuku;
        this.tglPeminjaman = tglPeminjaman;
        this.tglPengembalian = tglPengembalian;
        this.harga = harga;
    }

    public String getKodeBuku() {
        return kodeBuku;
    }
    public void setKodeBuku(String kodeBuku) {
        this.kodeBuku = kodeBuku;
    }
    public String getNamaBuku() {
        return namaBuku;
    }
    public void setNamaBuku(String namaBuku) {
        this.namaBuku = namaBuku;
    }
    public String getTglPeminjaman() {
        return tglPeminjaman;
    }
    public void setTglPeminjaman(String tglPeminjaman) {
        this.tglPeminjaman = tglPeminjaman;
    }
    public String getTglPengembalian() {
        return tglPengembalian;
    }
    public void setTglPengembalian(String tglPengembalian) {
        this.tglPengembalian = tglPengembalian;
    }
    public String getHarga() {
        return harga;
    }
    public void setHarga(String harga) {
        this.harga = harga;
    }
}
