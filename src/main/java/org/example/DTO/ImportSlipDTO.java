package org.example.DTO;

import java.sql.Date;
import java.sql.Time;

public class ImportSlipDTO {
    private String maPhieuNhap;
    private String maNCC;
    private String maNV;
    private Date ngayLap;
    private Time gioLap;
    private float tongTien;

    public ImportSlipDTO() {
    }

    public ImportSlipDTO(String maPhieuNhap, String maNCC, String maNV, Date ngayLap, Time gioLap, float tongTien) {
        this.maPhieuNhap = maPhieuNhap;
        this.maNCC = maNCC;
        this.maNV = maNV;
        this.ngayLap = ngayLap;
        this.gioLap = gioLap;
        this.tongTien = tongTien;
    }

    public String getMaPhieuNhap() {
        return maPhieuNhap;
    }

    public void setMaPhieuNhap(String maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }

    public String getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(String maNCC) {
        this.maNCC = maNCC;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public Date getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(Date ngayLap) {
        this.ngayLap = ngayLap;
    }

    public Time getGioLap() {
        return gioLap;
    }

    public void setGioLap(Time gioLap) {
        this.gioLap = gioLap;
    }

    public float getTongTien() {
        return tongTien;
    }

    public void setTongTien(float tongTien) {
        this.tongTien = tongTien;
    }
} 