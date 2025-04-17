package org.example.DAO;

import org.example.DTO.ImportSlipDetailDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImportSlipDetailDAO {
    private Connection conn = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    public List<ImportSlipDetailDTO> getDetailsByImportSlipId(String maPhieuNhap) {
        List<ImportSlipDetailDTO> details = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietPhieuNhap WHERE MaPhieuNhap=?";
        try {
            conn = JDBCUtil.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, maPhieuNhap);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                ImportSlipDetailDTO detail = new ImportSlipDetailDTO();
                detail.setMaPhieuNhap(rs.getString("MaPhieuNhap"));
                detail.setMaSP(rs.getString("MaSP"));
                detail.setSoLuong(rs.getInt("SoLuong"));
                detail.setDonGia(rs.getFloat("DonGia"));
                detail.setThanhTien(rs.getFloat("ThanhTien"));
                details.add(detail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(conn, pst, rs);
        }
        return details;
    }

    public boolean addImportSlipDetail(ImportSlipDetailDTO detail) {
        String sql = "INSERT INTO ChiTietPhieuNhap (MaPhieuNhap, MaSP, SoLuong, DonGia, ThanhTien) VALUES (?, ?, ?, ?, ?)";
        try {
            conn = JDBCUtil.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, detail.getMaPhieuNhap());
            pst.setString(2, detail.getMaSP());
            pst.setInt(3, detail.getSoLuong());
            pst.setFloat(4, detail.getDonGia());
            pst.setFloat(5, detail.getThanhTien());
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            JDBCUtil.closeConnection(conn, pst, null);
        }
    }

    public boolean updateImportSlipDetail(ImportSlipDetailDTO detail) {
        String sql = "UPDATE ChiTietPhieuNhap SET SoLuong=?, DonGia=?, ThanhTien=? WHERE MaPhieuNhap=? AND MaSP=?";
        try {
            conn = JDBCUtil.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, detail.getSoLuong());
            pst.setFloat(2, detail.getDonGia());
            pst.setFloat(3, detail.getThanhTien());
            pst.setString(4, detail.getMaPhieuNhap());
            pst.setString(5, detail.getMaSP());
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            JDBCUtil.closeConnection(conn, pst, null);
        }
    }

    public boolean deleteImportSlipDetail(String maPhieuNhap, String maSP) {
        String sql = "DELETE FROM ChiTietPhieuNhap WHERE MaPhieuNhap=? AND MaSP=?";
        try {
            conn = JDBCUtil.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, maPhieuNhap);
            pst.setString(2, maSP);
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            JDBCUtil.closeConnection(conn, pst, null);
        }
    }

    public boolean deleteAllDetailsForImportSlip(String maPhieuNhap) {
        String sql = "DELETE FROM ChiTietPhieuNhap WHERE MaPhieuNhap=?";
        try {
            conn = JDBCUtil.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, maPhieuNhap);
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            JDBCUtil.closeConnection(conn, pst, null);
        }
    }
} 