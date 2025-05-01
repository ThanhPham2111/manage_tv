package org.example.DTO;

public class InvoiceDetailDTO {
    private String maHD;
    private String maSP;
    private int soLuong;
    private float donGia;

    // Thuộc tính thêm cho bên "Xem chi tiết"
    private String tenSP;
    private double thanhTien;

    public InvoiceDetailDTO() {
        this.maHD = "";
        this.maSP = "";
        this.soLuong = 0;
        this.donGia = 0;
        this.tenSP = "";
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

    public String getTenSP(){
        return tenSP;
    }

    public void setTenSP(String tenSP){
        this.tenSP = tenSP;
    }

    public double getThanhTien(){
        return thanhTien;
    }

    public void setThanhTien(double thanhTien){
        this.thanhTien = thanhTien;
    }
}