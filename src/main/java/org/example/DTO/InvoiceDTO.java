package org.example.DTO;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;

import org.example.DateFormat.UtilsDateFormat;

public class InvoiceDTO {    
    private String maHoaDon;
    private String maKhachHang;
    private String maNhanVien;
    private String tenKhachHang;
    private String tenNhanVien;
    private Date ngayLap;
    private Time gioNhap;
    private double tongTien;
    private ArrayList<InvoiceDetailDTO> invoiceDetails;

    public InvoiceDTO() {
        maHoaDon = "";
        maKhachHang = "";
        maNhanVien = "";
        ngayLap = null;
        gioNhap = null;
        tongTien = 0;
        tenKhachHang = "";
        tenNhanVien = "";
        invoiceDetails = new ArrayList<>();
    }

    public InvoiceDTO(String maHoaDon, String maKhachHang, String maNhanVien, Date ngayLap, Time gioNhap, double tongTien) {
        this.maHoaDon = maHoaDon;
        this.maKhachHang = maKhachHang;
        this.maNhanVien = maNhanVien;
        this.ngayLap = ngayLap;
        this.gioNhap = gioNhap;
        this.tongTien = tongTien;
    }

    public void addInvoiceDetail(InvoiceDetailDTO invoiceDetail){
        invoiceDetails.add(invoiceDetail);
    }
    
    public void addInvoiceDetail(String maHoaDon, String maSanPham, long soLuong, double donGia){
        invoiceDetails.add(new InvoiceDetailDTO(maHoaDon, maSanPham, soLuong, donGia));
    }

    public ArrayList<InvoiceDetailDTO> getInvoiceDetails(){
        return invoiceDetails;
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

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public void calTongTien(){
        double sum = 0.0;
        for(InvoiceDetailDTO invoiceDetail : invoiceDetails){
            sum += invoiceDetail.getSoLuong() * invoiceDetail.getDonGia();
        }

        this.tongTien = sum;
    }
    
    public String getTenKhachHang(){
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang){
        this.tenKhachHang = tenKhachHang;
    }

    public String getTenNhanVien(){
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien){
        this.tenNhanVien = tenNhanVien;
    }
}