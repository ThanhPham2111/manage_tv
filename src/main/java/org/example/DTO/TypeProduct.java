package org.example.DTO;

public class TypeProduct {
    private String maLSP; // Đổi từ maLoaiSanPham thành maLSP
    private String tenLSP; // Đổi từ tenLoaiSanPham thành tenLSP
    private String moTa;

    public TypeProduct(String maLSP, String tenLSP, String moTa) {
        this.maLSP = maLSP;
        this.tenLSP = tenLSP;
        this.moTa = moTa;
    }

    public String getMaLSP() {
        return maLSP;
    }

    public void setMaLSP(String maLSP) {
        this.maLSP = maLSP;
    }

    public String getTenLSP() {
        return tenLSP;
    }

    public void setTenLSP(String tenLSP) {
        this.tenLSP = tenLSP;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}