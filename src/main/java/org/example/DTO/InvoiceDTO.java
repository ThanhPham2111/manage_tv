package org.example.DTO;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;

import org.example.UtilsDate.UtilsDateFormat;

public class InvoiceDTO {
    private String maHD;
    private String maKH;
    private String maNV;
    private Date ngayLap;
    private Time gioNhap;
    private double tongTien;
    private String tenKH;
    private String tenNV;

    public InvoiceDTO() {
        maHD = "";
        maKH = "";
        maNV = "";
        ngayLap = null;
        tongTien = 0;
        tenKH = "";
        tenNV = "";
    }

    public InvoiceDTO(String maHD, String maKH, String maNV, Date ngayLap, double tongTien) {
        this.maHD = maHD;
        this.maKH = maKH;
        this.maNV = maNV;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
        this.tenKH = "";
        this.tenNV = "";
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

    public String getTenKH(){
        return tenKH;
    }

    public void setTenKH(String tenKH){
        this.tenKH = tenKH;
    }

    public String getTenNV(){
        return tenNV;
    }

    public void setTenNV(String tenNV){
        this.tenNV = tenNV;
    }

    public java.util.Date getThoiGianLap(){
        try {
            String dateFormat = UtilsDateFormat.formatDate(ngayLap);
            String timeFormat = UtilsDateFormat.formatTime(gioNhap);
            java.util.Date date = UtilsDateFormat.stringToDate(dateFormat + " " + timeFormat);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setThoiGianLap(java.util.Date date){
        this.ngayLap = new java.sql.Date(date.getTime());
        this.gioNhap = new java.sql.Time(date.getTime());
    }
}