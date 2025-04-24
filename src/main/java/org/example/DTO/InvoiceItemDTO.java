package org.example.DTO;

public class InvoiceItemDTO{
    private String maSanPham;
    private long soLuong;
    private double donGia;

    public InvoiceItemDTO(){
        maSanPham = "";
        soLuong = 0;
        donGia = 0.0;
    }

    public InvoiceItemDTO(String maSanPhamr, long soLuong, double donGia){
        this.maSanPham = maSanPhamr;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public void setMaSanPham(String maSanPham){
        this.maSanPham = maSanPham;
    }

    public void setSoLuong(int soLuong){
        this.soLuong = soLuong;
    }

    public void setDonGia(double donGia){
        this.donGia = donGia;
    }

    public String getMaSanPham(){
        return maSanPham;
    }

    public double getDonGia(){
        return donGia;
    }

    public long getSoLuong(){
        return soLuong;
    }
}
