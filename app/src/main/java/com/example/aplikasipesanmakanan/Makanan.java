package com.example.aplikasipesanmakanan;

public class Makanan {
    private String namaPenyetan;
    String hargaPenyetan;
    private int imgPenyetan;

    public Makanan(String namaPenyetan, String hargaPenyetan, int imgPenyetan) {
        this.namaPenyetan = namaPenyetan;
        this.hargaPenyetan = hargaPenyetan;
        this.imgPenyetan = imgPenyetan;
    }

    public String getNamaPenyetan() {
        return namaPenyetan;
    }

    public void setNamaPenyetan(String namaPenyetan) {
        this.namaPenyetan = namaPenyetan;
    }

    public String getHargaPenyetan() {
        return hargaPenyetan;
    }

    public void setHargaPenyetan(String hargaPenyetan) {
        this.hargaPenyetan = hargaPenyetan;
    }

    public int getImgPenyetan() {
        return imgPenyetan;
    }

    public void setImgPenyetan(int imgPenyetan) {
        this.imgPenyetan = imgPenyetan;
    }
}
