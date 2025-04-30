package org.example.DTO;

import java.sql.Date;
import java.sql.Time;

public class InvoiceDTO {
    private String maHD;
    private String maKH;
    private String maNV;
    private Date ngayLap;
    private Time gioNhap;
    private double tongTien;

    public InvoiceDTO() {
        maHD = "";
        maKH = "";
        maNV = "";
        ngayLap = null;
        tongTien = 0;
    }

    public InvoiceDTO(String maHD, String maKH, String maNV, Date ngayLap, double tongTien) {
        this.maHD = maHD;
        this.maKH = maKH;
        this.maNV = maNV;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
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