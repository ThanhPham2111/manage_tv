package org.example.DTO;

public class InventoryDTO extends ProductDTO {
    private int soLuongTon;
    private String ngayNhap;
    private String ngayXuat;
    private String ghiChu;

    public InventoryDTO() {
        super();
        this.soLuongTon = 0;
        this.ngayNhap = "";
        this.ngayXuat = "";
        this.ghiChu = "";
    }

    public InventoryDTO(String maSP, String maLSP, String tenSP, float donGia, int soLuong, String hinhAnh,
            int trangthai,
            int soLuongTon, String ngayNhap, String ngayXuat, String ghiChu) {
        super(maSP, maLSP, tenSP, donGia, soLuong, hinhAnh, trangthai);
        this.soLuongTon = soLuongTon;
        this.ngayNhap = ngayNhap;
        this.ngayXuat = ngayXuat;
        this.ghiChu = ghiChu;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public String getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(String ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public String getNgayXuat() {
        return ngayXuat;
    }

    public void setNgayXuat(String ngayXuat) {
        this.ngayXuat = ngayXuat;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}