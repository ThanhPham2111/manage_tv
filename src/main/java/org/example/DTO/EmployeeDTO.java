package org.example.DTO;

import java.sql.Date;

public class EmployeeDTO {
    private String maNV;
    private String tenNV;
    private Date ngaySinh;
    private String diaChi;
    private String sdt;
    private int trangThai;

    // Constructor mặc định
    public EmployeeDTO() {
    }

    // Constructor đầy đủ tham số
    public EmployeeDTO(String maNV, String tenNV, Date ngaySinh, String diaChi, String sdt, int trangThai) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.trangThai = trangThai;
    }

    // Getter và Setter cho maNV
    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    // Getter và Setter cho tenNV
    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    // Getter và Setter cho ngaySinh
    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    // Getter và Setter cho diaChi
    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    // Getter và Setter cho sdt
    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    // Getter và Setter cho trangThai
    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}