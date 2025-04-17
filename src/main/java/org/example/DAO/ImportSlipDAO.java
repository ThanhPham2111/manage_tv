package org.example.DAO;

import org.example.DTO.ImportSlipDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImportSlipDAO {
    private Connection conn = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    public List<ImportSlipDTO> getAllImportSlips() {
        List<ImportSlipDTO> importSlips = new ArrayList<>();
        String sql = "SELECT * FROM PhieuNhap";
        try {
            conn = JDBCUtil.getConnection();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                ImportSlipDTO importSlip = new ImportSlipDTO();
                importSlip.setMaPhieuNhap(rs.getString("MaPhieuNhap"));
                importSlip.setMaNCC(rs.getString("MaNCC"));
                importSlip.setMaNV(rs.getString("MaNV"));
                importSlip.setNgayLap(rs.getDate("NgayLap"));
                importSlip.setGioLap(rs.getTime("GioLap"));
                importSlip.setTongTien(rs.getFloat("TongTien"));
                importSlips.add(importSlip);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(conn, pst, rs);
        }
        return importSlips;
    }

    public boolean addImportSlip(ImportSlipDTO importSlip) {
        String sql = "INSERT INTO PhieuNhap (MaPhieuNhap, MaNCC, MaNV, NgayLap, GioLap, TongTien) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            conn = JDBCUtil.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, importSlip.getMaPhieuNhap());
            pst.setString(2, importSlip.getMaNCC());
            pst.setString(3, importSlip.getMaNV());
            pst.setDate(4, new java.sql.Date(importSlip.getNgayLap().getTime()));
            pst.setTime(5, new java.sql.Time(importSlip.getGioLap().getTime()));
            pst.setFloat(6, importSlip.getTongTien());
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            JDBCUtil.closeConnection(conn, pst, null);
        }
    }

    public boolean updateImportSlip(ImportSlipDTO importSlip) {
        String sql = "UPDATE PhieuNhap SET MaNCC=?, MaNV=?, NgayLap=?, GioLap=?, TongTien=? WHERE MaPhieuNhap=?";
        try {
            conn = JDBCUtil.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, importSlip.getMaNCC());
            pst.setString(2, importSlip.getMaNV());
            pst.setDate(3, new java.sql.Date(importSlip.getNgayLap().getTime()));
            pst.setTime(4, new java.sql.Time(importSlip.getGioLap().getTime()));
            pst.setFloat(5, importSlip.getTongTien());
            pst.setString(6, importSlip.getMaPhieuNhap());
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            JDBCUtil.closeConnection(conn, pst, null);
        }
    }

    public boolean deleteImportSlip(String maPhieuNhap) {
        String sql = "DELETE FROM PhieuNhap WHERE MaPhieuNhap=?";
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

    public ImportSlipDTO getImportSlipById(String maPhieuNhap) {
        String sql = "SELECT * FROM PhieuNhap WHERE MaPhieuNhap=?";
        try {
            conn = JDBCUtil.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, maPhieuNhap);
            rs = pst.executeQuery();
            
            if (rs.next()) {
                ImportSlipDTO importSlip = new ImportSlipDTO();
                importSlip.setMaPhieuNhap(rs.getString("MaPhieuNhap"));
                importSlip.setMaNCC(rs.getString("MaNCC"));
                importSlip.setMaNV(rs.getString("MaNV"));
                importSlip.setNgayLap(rs.getDate("NgayLap"));
                importSlip.setGioLap(rs.getTime("GioLap"));
                importSlip.setTongTien(rs.getFloat("TongTien"));
                return importSlip;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(conn, pst, rs);
        }
        return null;
    }
} 