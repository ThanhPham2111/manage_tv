package org.example.DTO;

import java.sql.Date;
import java.sql.Time;

public class InvoiceDTO {
    private String maHoaDon;
    private String maKhachHang;
    private String maNhanVien;
    private Date ngayLap;
    private Time gioNhap;
    private double tongTien;

    public InvoiceDTO() {
        maHoaDon = "";
        maKhachHang = "";
        maNhanVien = "";
        ngayLap = null;
        tongTien = 0;
    }

    public InvoiceDTO(String maHoaDon, String maKhachHang, String maNhanVien, Date ngayLap, double tongTien) {
        this.maHoaDon = maHoaDon;
        this.maKhachHang = maKhachHang;
        this.maNhanVien = maNhanVien;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setmaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public Date getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(Date ngayLap) {
        this.ngayLap = ngayLap;
    }

    public Time getGioNhap() {
        return gioNhap;
    }

    public void setGioNhap(Time gioNhap) {
        this.gioNhap = gioNhap;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }
}