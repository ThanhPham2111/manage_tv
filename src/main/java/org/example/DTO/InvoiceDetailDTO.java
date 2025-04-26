package org.example.DTO;

public class InvoiceDetailDTO{
    private String maHoaDon;
    private String maSanPham;
    private long soLuong;
    private double donGia;

    // Thuộc tính thêm cho bên "Xem chi tiết"
    private String tenSanPham;
    private Double thanhTien;

    public InvoiceDetailDTO(){
        maSanPham = "";
        tenSanPham = "";
        soLuong = 0;
        donGia = 0.0;
        thanhTien = 0.0;
    }

    public InvoiceDetailDTO(String maHoaDon, String maSanPham, long soLuong, double donGia){
        this.maHoaDon = maHoaDon;
        this.maSanPham = maSanPham;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    // Constructor cho bên "Xem chi tiết"
    public InvoiceDetailDTO(String maSanPham, String tenSanPham, long soLuong, double donGia, double thanhTien){
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = thanhTien;
    }

    public String getMaHoaDon(){
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon){
        this.maHoaDon = maHoaDon;
    }

    public String getMaSanPham(){
        return maSanPham;
    }
    
    public void setMaSanPham(String maSanPham){
        this.maSanPham = maSanPham;
    }

    public long getSoLuong(){
        return soLuong;
    }

    public void setSoLuong(int soLuong){
        this.soLuong = soLuong;
    }

    public double getDonGia(){
        return donGia;
    }
    
    public void setDonGia(double donGia){
        this.donGia = donGia;
    }

    public String getTenSanPham(){
        return tenSanPham;
    }

    public double getThanhTien(){
        return thanhTien;
    }
}
