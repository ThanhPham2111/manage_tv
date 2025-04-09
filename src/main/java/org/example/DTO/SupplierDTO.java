package org.example.DTO;

public class SupplierDTO {
    String MaNCC, TenNCC, DiaChi, SDT, Fax;
    int TrangThai;

    public SupplierDTO() {
        super();
    }

    public SupplierDTO(String maNCC, String tenNCC, String diaChi, String sDT, String fax, int trangThai) {
        super();
        MaNCC = maNCC;
        TenNCC = tenNCC;
        DiaChi = diaChi;
        SDT = sDT;
        Fax = fax;
        TrangThai = trangThai;
    }

    public String getMaNCC() {
        return MaNCC;
    }

    public void setMaNCC(String maNCC) {
        MaNCC = maNCC;
    }

    public String getTenNCC() {
        return TenNCC;
    }

    public void setTenNCC(String tenNCC) {
        TenNCC = tenNCC;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String sDT) {
        SDT = sDT;
    }

    public String getFax() {
        return Fax;
    }

    public void setFax(String fax) {
        Fax = fax;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int trangThai) {
        TrangThai = trangThai;
    }

}
