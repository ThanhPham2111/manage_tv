package org.example.DTO;

public class InvoiceDetailDTO {
    private String maHD;
    private String maSP;
    private int soLuong;
    private float donGia;

    public InvoiceDetailDTO() {
        this.maHD = "";
        this.maSP = "";
        this.soLuong = 0;
        this.donGia = 0;
    }

    public InvoiceDetailDTO(String maHD, String maSP, int soLuong, float donGia) {
        this.maHD = maHD;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public float getDonGia() {
        return donGia;
    }

    public void setDonGia(float donGia) {
        this.donGia = donGia;
    }
}