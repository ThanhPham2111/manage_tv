package org.example.DTO;

import java.sql.Date;
import java.sql.Time;

public class ImportDTO {
    private String maPN;        
    private String maNCC;
    private String maNV;
    private Date ngayNhap;      
    private Time gioNhap;         
    private float tongTien;

    public ImportDTO() {
    }

    public ImportDTO(String maPN, String maNCC, String maNV, Date ngayNhap, Time gioNhap, float tongTien) {
        this.maPN = maPN;
        this.maNCC = maNCC;
        this.maNV = maNV;
        this.ngayNhap = ngayNhap;
        this.gioNhap = gioNhap;
        this.tongTien = tongTien;
    }

    public String getMaPN() {
        return maPN;
    }

    public void setMaPN(String maPN) {
        this.maPN = maPN;
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

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public Time getGioNhap() {
        return gioNhap;
    }

    public void setGioNhap(Time gioNhap) {
        this.gioNhap = gioNhap;
    }

    public float getTongTien() {
        return tongTien;
    }

    public void setTongTien(float tongTien) {
        this.tongTien = tongTien;
    }
}