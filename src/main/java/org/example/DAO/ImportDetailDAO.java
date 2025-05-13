package org.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.example.ConnectDB.UtilsJDBC;
import org.example.DTO.ImportDetailDTO;

public class ImportDetailDAO {
    private Connection conn = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    public List<ImportDetailDTO> getDetailsByImportSlipId(String maPhieuNhap) {
        List<ImportDetailDTO> details = new ArrayList<>();
        String sql = "SELECT id.*, p.TenSP, (id.DonGia * id.SoLuong) as ThanhTien, " +
                    "i.TongTien as TongTienPhieuNhap " +
                    "FROM import_details id " +
                    "JOIN product p ON id.MaSP = p.MaSP " +
                    "JOIN import i ON id.MaPN = i.MaPN " +
                    "WHERE id.MaPN=?";
        try {
            conn = UtilsJDBC.getConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setString(1, maPhieuNhap);
            rs = pst.executeQuery();

            float tongTienChiTiet = 0;
            float tongTienPhieuNhap = 0;

            while (rs.next()) {
                ImportDetailDTO detail = new ImportDetailDTO();
                detail.setMaPN(rs.getString("MaPN"));
                detail.setMaSP(rs.getString("MaSP")); 
                detail.setSoLuong(rs.getInt("SoLuong"));
                detail.setDonGia(rs.getFloat("DonGia"));
                float thanhTien = rs.getFloat("ThanhTien");
                detail.setThanhTien(thanhTien);
                tongTienChiTiet += thanhTien;
                tongTienPhieuNhap = rs.getFloat("TongTienPhieuNhap");
                details.add(detail);
            }

            // Kiểm tra nếu có sự chênh lệch
            if (Math.abs(tongTienChiTiet - tongTienPhieuNhap) > 0.01) {
                System.out.println("Cảnh báo: Có sự chênh lệch giữa tổng tiền chi tiết (" + 
                    tongTienChiTiet + ") và tổng tiền phiếu nhập (" + tongTienPhieuNhap + ")");
                // Cập nhật lại tổng tiền trong bảng import nếu cần
                updateTotalAmount(maPhieuNhap, tongTienChiTiet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            UtilsJDBC.closeConnection();
        }
        return details;
    }

    private void updateTotalAmount(String maPhieuNhap, float newTotal) {
        String sql = "UPDATE import SET TongTien = ? WHERE MaPN = ?";
        try {
            conn = UtilsJDBC.getConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setFloat(1, newTotal);
            pst.setString(2, maPhieuNhap);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            UtilsJDBC.closeConnection();
        }
    }

    public boolean addImportDetail(ImportDetailDTO detail) {
        String query = "INSERT INTO import_details (MaPN, MaSP, SoLuong, DonGia) VALUES (?, ?, ?, ?)";
        try {
            conn = UtilsJDBC.getConnectDB();
            pst = conn.prepareStatement(query);
            pst.setString(1, detail.getMaPN());
            pst.setString(2, detail.getMaSP());
            pst.setInt(3, detail.getSoLuong());
            pst.setFloat(4, detail.getDonGia());
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            UtilsJDBC.closeConnection();
        }
    }

    public boolean updateImportSlipDetail(ImportDetailDTO detail) {
        String sql = "UPDATE import_details SET SoLuong=?, DonGia=? WHERE MaPN=? AND MaSP=?";
        try {
            conn = UtilsJDBC.getConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, detail.getSoLuong());
            pst.setFloat(2, detail.getDonGia());
            pst.setString(3, detail.getMaPN());
            pst.setString(4, detail.getMaSP());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            UtilsJDBC.closeConnection();
        }
    }

    public boolean deleteImportSlipDetail(String maPhieuNhap, String maSP) {
        String sql = "DELETE FROM import_details WHERE MaPN=? AND MaSP=?";
        try {
            conn = UtilsJDBC.getConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setString(1, maPhieuNhap);
            pst.setString(2, maSP);

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            UtilsJDBC.closeConnection();
        }
    }

    public boolean deleteAllDetailsForImportSlip(String maPhieuNhap) {
        String sql = "DELETE FROM import_details WHERE MaPN=?";
        try {
            conn = UtilsJDBC.getConnectDB();
            pst = conn.prepareStatement(sql);
            pst.setString(1, maPhieuNhap);

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            UtilsJDBC.closeConnection();
        }
    }
}