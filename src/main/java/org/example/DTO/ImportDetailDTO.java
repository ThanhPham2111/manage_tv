package org.example.DTO;

public class ImportDetailDTO {
    private String maPN;          // Thay đổi từ maPhieuNhap theo DB
    private String maSP;
    private int soLuong;
    private float donGia;
    private float thanhTien;      // Calculated field: soLuong * donGia

    public ImportDetailDTO() {
    }

    public ImportDetailDTO(String maPN, String maSP, int soLuong, float donGia) {
        this.maPN = maPN;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = soLuong * donGia;
    }

    public String getMaPN() {
        return maPN;
    }

    public void setMaPN(String maPN) {
        this.maPN = maPN;
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

    public float getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(float thanhTien){
        this.thanhTien = this.soLuong*this.donGia;
    }
}