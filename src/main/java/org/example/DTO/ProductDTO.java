    package org.example.DTO;

    public class ProductDTO {
        // "MãSP","MãLSP", "TênSP", "Đơn giá", "File ảnh", "Số lượng", "Trạng thái"
        private String MaSP, MaLSP, TenSP, HinhAnh;
        private int SoLuong, Trangthai;
        private float DonGia;

        public ProductDTO() {

        }

        public ProductDTO(String maSP, String maLSP, String tenSP, float donGia, int soLuong, String hinhAnh, int trangthai) {
            MaSP = maSP;
            MaLSP = maLSP;
            TenSP = tenSP;
            HinhAnh = hinhAnh;
            SoLuong = soLuong;
            Trangthai = trangthai;
            DonGia = donGia;
        }

        public String getMaSP() {
            return MaSP;
        }

        public void setMaSP(String maSP) {
            MaSP = maSP;
        }

        public String getMaLSP() {
            return MaLSP;
        }

        public void setMaLSP(String maLSP) {
            MaLSP = maLSP;
        }

        public String getTenSP() {
            return TenSP;
        }

        public void setTenSP(String tenSP) {
            TenSP = tenSP;
        }

        public String getHinhAnh() {
            return HinhAnh;
        }

        public void setHinhAnh(String hinhAnh) {
            HinhAnh = hinhAnh;
        }

        public int getSoLuong() {
            return SoLuong;
        }

        public void setSoLuong(int soLuong) {
            SoLuong = soLuong;
        }

        public int getTrangthai() {
            return Trangthai;
        }

        public void setTrangthai(int trangthai) {
            Trangthai = trangthai;
        }

        public float getDonGia() {
            return DonGia;
        }

        public void setDonGia(float donGia) {
            DonGia = donGia;
        }

    }
