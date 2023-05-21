package com.example.model;

public class Book {
    private String idBooks;
    private String namaBuku;
    private Float harga;

    public Book(String idBooks, String namaBuku, Float harga) {
        this.idBooks = idBooks;
        this.namaBuku = namaBuku;
        this.harga = harga;
    }
    
    public String getIdBooks() {
        return idBooks;
    }
    public void setIdBooks(String idBooks) {
        this.idBooks = idBooks;
    }
    public String getNamaBuku() {
        return namaBuku;
    }
    public void setNamaBuku(String namaBuku) {
        this.namaBuku = namaBuku;
    }
    public Float getHarga() {
        return harga;
    }
    public void setHarga(Float harga) {
        this.harga = harga;
    }
}
